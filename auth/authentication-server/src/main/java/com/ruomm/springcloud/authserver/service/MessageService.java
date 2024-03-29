package com.ruomm.springcloud.authserver.service;

import com.ruomm.javax.corex.ListUtils;
import com.ruomm.javax.corex.RegexUtils;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.corex.TimeUtils;
import com.ruomm.springcloud.authserver.config.AppConfig;
import com.ruomm.springcloud.authserver.config.ConfigProperties;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.core.MsgContentByTemplate;
import com.ruomm.springcloud.authserver.dal.request.MessageSendReq;
import com.ruomm.springcloud.authserver.dal.request.sub.ReqKeyValuePair;
import com.ruomm.springcloud.authserver.dao.MsgContentMapper;
import com.ruomm.springcloud.authserver.dao.MsgTemplateMapper;
import com.ruomm.springcloud.authserver.dao.UserMapper;
import com.ruomm.springcloud.authserver.entry.MsgContentEntity;
import com.ruomm.springcloud.authserver.entry.MsgTemplateEntity;
import com.ruomm.springcloud.authserver.entry.UserEntity;
import com.ruomm.springcloud.authserver.exception.WebAppException;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import com.ruomm.springcloud.authserver.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 0:54
 */
@Slf4j
@Service
public class MessageService {
    @Autowired
    ConfigProperties configProperties;
    private static final int Template_Status_Ok = 1;
    private static final int Msg_Status_Enable = 1;
    private static final int Msg_Status_Valid_Ok = 2;
    private static final int Msg_Status_Valid_Err = 3;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MsgContentMapper msgContentMapper;
    @Autowired
    MsgTemplateMapper msgTemplateMapper;

    public CommonResponse send(String tpl_key, MessageSendReq req) {
        // 设置默认的信息类型为手机号
        String msgType = StringUtils.isEmpty(req.getMsgType()) ? "mobile" : req.getMsgType();
        req.setMsgType(msgType);
        // 获取消息模板
        MsgTemplateEntity msgTemplate = queryMsgTemplateEntity(tpl_key);
        // 获取用户信息
        UserEntity userEntity = parseUserEntityByTemplate(msgTemplate, req.getUserId());
        // 获取信息发送地址
        String msgAddr = parseMsgAddr(msgTemplate, userEntity, req);
        req.setMsgAddr(msgAddr);
        // 获取日期
        Date dateToday = null;
        try {
            String dateStr = TimeUtils.formatTime(System.currentTimeMillis(), AppConfig.DATE_FORMAT_MESSAGE);
            dateStr = dateStr.substring(0, 10) + " 00:00:00";
            dateToday = AppConfig.DATE_FORMAT_MESSAGE.parse(dateStr);
        } catch (ParseException e) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,查询日期转换错误", msgTemplate.getTplName()));
        }
        verifyLimitByMsgAddr(msgTemplate, req, dateToday);
        verifyLimitByClientInfo(msgTemplate, req, dateToday);
        verifyLimitByUserId(msgTemplate, userEntity, dateToday);
        verifyRepeatSkipTime(msgTemplate, req);
        //  获取真正需要发送的信息内容
        MsgContentByTemplate msgContentByTemplate = parseMsgContentByTemplate(msgTemplate, userEntity, req);
        // 开始发送短信
        Date dateNow = new Date();
        MsgContentEntity msgContentEntity = new MsgContentEntity();
        msgContentEntity.setAliasId(AppConfig.TOKEN_HELPER.generateToken(32));
        msgContentEntity.setTplKey(msgTemplate.getTplKey());
        if (null != userEntity) {
            msgContentEntity.setUserId(userEntity.getId());
        }
        msgContentEntity.setMsgType(req.getMsgType());
        msgContentEntity.setMsgAddr(req.getMsgAddr());
        msgContentEntity.setMsgContent(msgContentByTemplate.getContent());
        msgContentEntity.setVerifyCode(msgContentByTemplate.getVerifyCode());
        msgContentEntity.setValidTime(msgContentByTemplate.getValidTime());
        msgContentEntity.setCheckCount(0);
        msgContentEntity.setStatus(1);
        msgContentEntity.setClientIp(req.getClientIp());
        msgContentEntity.setVersion(1);
        msgContentEntity.setCreatedAt(dateNow);
        msgContentEntity.setUpdatedAt(dateNow);
        int updateResult = msgContentMapper.insertSelective(msgContentEntity);
        if (updateResult != 1) {
            log.error("消息发送失败，消息内容为：" + msgContentEntity.getMsgContent());
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,入库失败", msgTemplate.getTplName()));
        }
        log.info("消息发送成功，消息内容为：" + msgContentEntity.getMsgContent());
        return AppUtils.toAck();
    }

    public boolean valid(String tpl_key, String msgType, String msgAddr, Long userId, String verifyCode,String clientId, String clientIp, String clientHost) {

        // 获取消息模板
        MsgTemplateEntity msgTemplate = queryMsgTemplateEntityForValid(tpl_key);
        String tpl_name = msgTemplate.getTplName();
        String msgTypeReal = StringUtils.isEmpty(msgType) ? "mobile" : msgType;
        // 验证码发送地址和用户ID必须有一个
        if (StringUtils.isEmpty(msgAddr) && (null == userId || userId.longValue()<=0)){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证失败，验证参数错误",tpl_name));
        }
        // 依据模板查找短信条数
        Example exampleForMsg = new Example(MsgContentEntity.class);
        Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
        criteriaForMsg.andEqualTo("tplKey", tpl_key);
        criteriaForMsg.andEqualTo("status", 1);
        criteriaForMsg.andEqualTo("msgType", msgTypeReal);
        if (!StringUtils.isEmpty(msgAddr)) {
            criteriaForMsg.andEqualTo("msgAddr", msgAddr);
        }
        if (null != userId && userId.longValue() > 0) {
            criteriaForMsg.andEqualTo("userId", userId);
        }
        if (!StringUtils.isEmpty(clientId)) {
            criteriaForMsg.andEqualTo("clientId", clientId);
        }
        if (!StringUtils.isEmpty(clientHost)) {
            criteriaForMsg.andEqualTo("clientHost", clientHost);
        }
        if (!StringUtils.isEmpty(clientIp)) {
            criteriaForMsg.andEqualTo("clientIp", clientIp);
        }
        Date dateNow = new Date();
        Date dateQuery = new Date(dateNow.getTime() - 1000l * 3600);
        criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateQuery);
        exampleForMsg.setOrderByClause("created_at desc");
        List<MsgContentEntity> listMsgContent = msgContentMapper.selectByExample(exampleForMsg);

        MsgContentEntity contentEntity = ListUtils.isEmpty(listMsgContent) ? null : listMsgContent.get(0);
        if (null == contentEntity || StringUtils.isEmpty(contentEntity.getVerifyCode())) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码还没有发送",tpl_name));
        }
        if (null == contentEntity.getValidTime() || contentEntity.getValidTime().intValue()<=0){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码已过期",tpl_name));
        }
        // 判断验证码是否过期
        long validStart = contentEntity.getCreatedAt().getTime();
        long validEnd = contentEntity.getCreatedAt().getTime()+contentEntity.getValidTime().intValue()*1000l;
        long validNow = dateNow.getTime();
        if (validNow<validStart || validNow>validEnd){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码已过期",tpl_name));
        }
        if(contentEntity.getStatus().intValue() != 1){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码已使用", tpl_name));
        }
        if (contentEntity.getCheckCount().intValue()>=AppConfig.VERIFY_CODE_CHECK_COUNT){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码已过期", tpl_name));
        }
        boolean verifyCodePass =false;
        int checkCount = contentEntity.getCheckCount().intValue()+1;
        MsgContentEntity contentEntityUpdate = new MsgContentEntity();
        contentEntityUpdate.setId(contentEntity.getId());
        contentEntityUpdate.setCheckCount(checkCount);
        contentEntityUpdate.setVersion(contentEntity.getVersion());
        // 判断验证码是否正确
        if (!contentEntity.getVerifyCode().equalsIgnoreCase(verifyCode)){
            if (checkCount >=AppConfig.VERIFY_CODE_CHECK_COUNT){
                contentEntityUpdate.setStatus(3);
            }
        } else {
            verifyCodePass = true;
            contentEntityUpdate.setStatus(2);
        }
        int dbUpdate = msgContentMapper.updateByPrimaryKeySelective(contentEntityUpdate);
        if (dbUpdate!=1){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证失败，数据处理错误", tpl_name));
        }
        if (!verifyCodePass){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s验证码不正确", tpl_name));
        }
        return true;
    }

    /**
     * 查找短信模块
     *
     * @param tpl_key 短信模板key
     * @return 短信模块
     */
    private MsgTemplateEntity queryMsgTemplateEntity(String tpl_key) {
        MsgTemplateEntity queryObj = new MsgTemplateEntity();
        queryObj.setTplKey(tpl_key);
        MsgTemplateEntity resultObj = msgTemplateMapper.selectByPrimaryKey(queryObj);
        if (null == resultObj) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板不存在，信息无法发送");
        }
        if (null == resultObj.getStatus() || resultObj.getStatus() != Template_Status_Ok) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板已停用，信息无法发送");
        }
        if (StringUtils.isEmpty(resultObj.getTemplate())) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板无内容，信息无法发送");
        }

        if (null == resultObj.getAuthType()) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板授权类型为空，信息无法发送");
        }
        return resultObj;
    }

    /**
     * 查找短信模块
     *
     * @param tpl_key 短信模板key
     * @return 短信模块
     */
    private MsgTemplateEntity queryMsgTemplateEntityForValid(String tpl_key) {
        MsgTemplateEntity queryObj = new MsgTemplateEntity();
        queryObj.setTplKey(tpl_key);
        MsgTemplateEntity resultObj = msgTemplateMapper.selectByPrimaryKey(queryObj);
        if (null == resultObj) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板不存在，无法验证");
        }
        if (null == resultObj.getStatus() || resultObj.getStatus() != Template_Status_Ok) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板已停用，无法验证");
        }
        if (StringUtils.isEmpty(resultObj.getTemplate())) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板无内容，无法验证");
        }

        if (null == resultObj.getAuthType()) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板授权类型为空，无法验证");
        }
        return resultObj;
    }

    // 依据消息模板获取并验证用户信息
    private UserEntity parseUserEntityByTemplate(MsgTemplateEntity msgTemplateEntity, Long userId) {
        int authType = msgTemplateEntity.getAuthType().intValue();
        String tplName = msgTemplateEntity.getTplName();
        if (authType == 0) {
            return null;
        } else if (authType == 1 || authType == 2) {
            if (null == userId || userId <= 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            UserEntity queryUser = new UserEntity();
            queryUser.setId(userId);
            UserEntity resultUser = userMapper.selectByPrimaryKey(queryUser);
            if (null == resultUser) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            int tplUserStatus = null == msgTemplateEntity.getUserStatus() ? 0 : msgTemplateEntity.getUserStatus().intValue();
            if (tplUserStatus <= 0) {
                if (resultUser.getStatus().intValue() != AppConfig.DB_STATUS_OK) {
                    throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户状态：%s", tplName, WebUtils.parseUserStatus(resultUser.getStatus())));
                }
            } else {
                if (resultUser.getStatus().intValue() != tplUserStatus) {
                    throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户状态：%s", tplName, WebUtils.parseUserStatus(resultUser.getStatus())));
                }
            }
            return resultUser;
        } else if (authType == 3 || authType == 4) {
            if (null == userId || userId <= 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            UserEntity queryUser = new UserEntity();
            queryUser.setId(userId);
            UserEntity resultUser = userMapper.selectByPrimaryKey(queryUser);
            if (null == resultUser) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            int tplUserStatus = null == msgTemplateEntity.getUserStatus() ? 0 : msgTemplateEntity.getUserStatus().intValue();
            if (tplUserStatus <= 0) {
                if (resultUser.getStatus().intValue() == 0 || resultUser.getStatus().intValue() == 9) {
                    throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户状态：%s", tplName, WebUtils.parseUserStatus(resultUser.getStatus())));
                }
            } else {
                if (resultUser.getStatus().intValue() != tplUserStatus) {
                    throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户状态：%s", tplName, WebUtils.parseUserStatus(resultUser.getStatus())));
                }
            }
            return resultUser;
        } else {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,消息模板授权控制不正确", tplName));
        }
    }

    // 解析消息发送地址
    private String parseMsgAddr(MsgTemplateEntity msgTemplateEntity, UserEntity userEntity, MessageSendReq req) {
        String tplName = msgTemplateEntity.getTplName();
        String msgType = req.getMsgType();
        String msgAddr = null;
        int authType = msgTemplateEntity.getAuthType().intValue();
        if (authType == 0) {
            msgAddr = req.getMsgAddr();
        } else if (authType == 1) {
            msgAddr = req.getMsgAddr();
        } else if (authType == 2) {
            msgAddr = parseBindMsgAddr(tplName, msgType, req.getMsgAddr(), userEntity);
        } else if (authType == 3) {
            msgAddr = req.getMsgAddr();
        } else if (authType == 4) {
            msgAddr = parseBindMsgAddr(tplName, msgType, req.getMsgAddr(), userEntity);
        } else {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,消息模板授权控制不正确", tplName));
        }
        if (StringUtils.isEmpty(msgAddr)) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,%s不能为空", tplName, WebUtils.parseMsgType(msgType)));
        }
        if (StringUtils.isEquals(msgType, "mobile")) {
            if (!RegexUtils.doRegex(msgAddr, AppConfig.REGEX_MOBILE)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,%s不合法", tplName, WebUtils.parseMsgType(msgType)));
            }
        } else if (StringUtils.isEquals(msgType, "email")) {
            if (!RegexUtils.doRegex(msgAddr, AppConfig.REGEX_EMAIL)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,%s不合法", tplName, WebUtils.parseMsgType(msgType)));
            }
        } else if (StringUtils.isEquals(msgType, "weixin")) {
            int msgAddrLength = StringUtils.getLength(msgAddr);
            if (msgAddrLength <= 0 || msgAddrLength > 64) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,%s不合法", tplName, WebUtils.parseMsgType(msgType)));
            }
        } else if (StringUtils.isEquals(msgType, "qq")) {
            if (!RegexUtils.doRegex(msgAddr, AppConfig.REGEX_QQ)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,%s不合法", tplName, WebUtils.parseMsgType(msgType)));
            }
        }
        return msgAddr;
    }

    // 获取用户绑定的信息发送地址
    private String parseBindMsgAddr(String tplName, String msgType, String reqMsgAddr, UserEntity userEntity) {
        String msgAddr = null;
        if (StringUtils.isEquals(msgType, "mobile")) {
            String msgAddrInDb = userEntity.getBindPhone();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "email")) {
            String msgAddrInDb = userEntity.getBindEmail();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "weixin")) {
            String msgAddrInDb = userEntity.getBindWeixin();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "qq")) {
            String msgAddrInDb = userEntity.getBindQq();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的%s", tplName, WebUtils.parseMsgType(msgType)));
            }
            msgAddr = msgAddrInDb;
        }
        if (StringUtils.isEmpty(msgAddr)) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的%s", tplName, WebUtils.parseMsgType(msgType)));
        }
        return msgAddr;
    }

    // 获取真正需要发送的信息内容
    private MsgContentByTemplate parseMsgContentByTemplate(MsgTemplateEntity msgTemplate, UserEntity userEntity, MessageSendReq req) {
        MsgContentByTemplate msgContentByTemplate = new MsgContentByTemplate();
        String template = msgTemplate.getTemplate();
        //${year},${month},${day},${time},${datetime},${userId},${userName}
        //yyyy-MM-dd HH:mm:ss
        String dateStr = TimeUtils.formatTime(System.currentTimeMillis(), AppConfig.DATE_FORMAT_MESSAGE);
        template = template.replace("${year}", dateStr.substring(0, 4));
        template = template.replace("${month}", dateStr.substring(5, 7));
        template = template.replace("${day}", dateStr.substring(8, 10));
        template = template.replace("${time}", dateStr.substring(11, 19));
        template = template.replace("${datetime}", dateStr);
        template = template.replace("${appname}", configProperties.getVerifyCodeConfig().getAppName());
        if (null != userEntity) {
            template = template.replace("${userId}", userEntity.getId() + "");
            template = template.replace("${userName}", userEntity.getUserName());
            template = template.replace("${nickName}", userEntity.getNickName());
        }
        String verifyCode = null;
        if (template.contains("${verifycode}")) {
            verifyCode = AppConfig.TOKEN_HELPER_MSG.generateToken();
            template = template.replace("${verifycode}", verifyCode);
//            解析验证码有效期
            int validTime = WebUtils.parseMsgValidTime(msgTemplate, configProperties);
            String validTimeStr = WebUtils.formatMsgValidTime(validTime);
            template = template.replace("${validtime}", validTimeStr);
            msgContentByTemplate.setVerifyCode(verifyCode);
            msgContentByTemplate.setValidTime(validTime);
            msgContentByTemplate.setValidTimeStr(validTimeStr);
        }
        // 开始依据参数替换消息模板
        int msgPairsSize = ListUtils.getSize(req.getMsgPairs());
        for (int i = 0; i < msgPairsSize; i++) {
            ReqKeyValuePair keyValuePair = req.getMsgPairs().get(i);
            if (StringUtils.isEmpty(keyValuePair.getKey())) {
                continue;
            }
            template = template.replace("${" + keyValuePair.getKey() + "}", StringUtils.nullStrToEmpty(keyValuePair.getValue()));
        }
        if (template.contains("${")) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,消息模板解析失败，部分字段没有赋值", msgTemplate.getTplName()));
        }
        msgContentByTemplate.setContent(template);
        return msgContentByTemplate;
    }

    // 验证短信发送限制-同一发送目标
    private boolean verifyLimitByMsgAddr(MsgTemplateEntity msgTemplate, MessageSendReq req, Date dateToday) {
        String tplName = msgTemplate.getTplName();
        if (null != msgTemplate.getLimitByAddr() && msgTemplate.getLimitByAddr().intValue() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
            criteriaForMsg.andEqualTo("tplKey", msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("msgAddr", req.getMsgAddr());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= msgTemplate.getLimitByAddr().intValue()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一%s发送限制，限制次数为%s", tplName, WebUtils.parseMsgType(req.getMsgType()), msgTemplate.getLimitByAddr().intValue() + ""));
            }
        }
        if (configProperties.getVerifyCodeConfig().getLimitByAddr() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
//            criteriaForMsg.andEqualTo("tplKey",msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("msgAddr", req.getMsgAddr());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= configProperties.getVerifyCodeConfig().getLimitByAddr()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一%s发送限制，限制次数为%s", tplName, WebUtils.parseMsgType(req.getMsgType()), configProperties.getVerifyCodeConfig().getLimitByAddr() + ""));
            }
        }
        return true;
    }

    // 验证短信发送限制-同一用户
    private boolean verifyLimitByUserId(MsgTemplateEntity msgTemplate, UserEntity userEntity, Date dateToday) {
        if (null == userEntity) {
            // 跳过用户验证
            return true;
        }
        String tplName = msgTemplate.getTplName();
        if (null != msgTemplate.getLimitByUser() && msgTemplate.getLimitByUser().intValue() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
            criteriaForMsg.andEqualTo("tplKey", msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("userId", userEntity.getId());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= msgTemplate.getLimitByUser().intValue()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一用户发送限制，限制次数为%s", tplName, msgTemplate.getLimitByUser().intValue() + ""));
            }
        }
        if (configProperties.getVerifyCodeConfig().getLimitByUser() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
//            criteriaForMsg.andEqualTo("tplKey",msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("userId", userEntity.getId());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= configProperties.getVerifyCodeConfig().getLimitByUser()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一用户发送限制，限制次数为%s", tplName, configProperties.getVerifyCodeConfig().getLimitByUser() + ""));
            }
        }
        return true;
    }

    // 验证短信发送限制-同一客户端
    private boolean verifyLimitByClientInfo(MsgTemplateEntity msgTemplate, MessageSendReq req, Date dateToday) {
        String tplName = msgTemplate.getTplName();
        if (null != msgTemplate.getLimitByTerm() && msgTemplate.getLimitByTerm().intValue() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
            criteriaForMsg.andEqualTo("tplKey", msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("clientIp", req.getClientIp());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= msgTemplate.getLimitByTerm().intValue()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一客户端发送限制，限制次数为%s", tplName, msgTemplate.getLimitByTerm().intValue() + ""));
            }
        }
        if (configProperties.getVerifyCodeConfig().getLimitByTerm() > 0) {
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
//            criteriaForMsg.andEqualTo("tplKey",msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("clientIp", req.getClientIp());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateToday);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count >= configProperties.getVerifyCodeConfig().getLimitByTerm()) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,超过该接口当天同一客户端发送限制，限制次数为%s", tplName, configProperties.getVerifyCodeConfig().getLimitByTerm() + ""));
            }
        }
        return true;
    }

    // 验证短信再次发送间隔时间
    private boolean verifyRepeatSkipTime(MsgTemplateEntity msgTemplate, MessageSendReq req) {
        String tplName = msgTemplate.getTplName();
        Date dateNow = new Date();
        if (null != msgTemplate.getRepeatSkipTime() && msgTemplate.getRepeatSkipTime().intValue() > 0) {
            Date dateQuery = new Date(dateNow.getTime() - msgTemplate.getRepeatSkipTime().intValue() * 1000l);
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
            criteriaForMsg.andEqualTo("tplKey", msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("clientIp", req.getClientIp());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateQuery);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count > 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,再次发送间隔时间太短。", tplName));
            }
        }
        if (configProperties.getVerifyCodeConfig().getRepeatSkipTime() > 0) {
            Date dateQuery = new Date(dateNow.getTime() - configProperties.getVerifyCodeConfig().getRepeatSkipTime() * 1000l);
            // 依据模板查找短信条数
            Example exampleForMsg = new Example(MsgContentEntity.class);
            Example.Criteria criteriaForMsg = exampleForMsg.createCriteria();
//            criteriaForMsg.andEqualTo("tplKey",msgTemplate.getTplKey());
            criteriaForMsg.andIsNotNull("status");
            criteriaForMsg.andGreaterThan("status", 0);
            criteriaForMsg.andEqualTo("clientIp", req.getClientIp());
            criteriaForMsg.andGreaterThanOrEqualTo("createdAt", dateQuery);
            int count = msgContentMapper.selectCountByExample(exampleForMsg);
            if (count > 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,再次发送间隔时间太短。", tplName));
            }
        }
        return true;
    }
}

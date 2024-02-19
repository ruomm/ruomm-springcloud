package com.ruomm.springcloud.authserver.service;

import com.ruomm.javax.corex.StringUtils;
import com.ruomm.springcloud.authserver.config.AppConfig;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.MessageSendReq;
import com.ruomm.springcloud.authserver.dao.MsgContentMapper;
import com.ruomm.springcloud.authserver.dao.MsgTemplateMapper;
import com.ruomm.springcloud.authserver.dao.UserMapper;
import com.ruomm.springcloud.authserver.entry.MsgTemplateEntity;
import com.ruomm.springcloud.authserver.entry.UserEntity;
import com.ruomm.springcloud.authserver.exception.WebAppException;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 0:54
 */
@Slf4j
@Service
public class MessageService {
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

    public CommonResponse send(String tpl_key,MessageSendReq req) {
        MsgTemplateEntity msgTemplate = queryMsgTemplateEntity(tpl_key,0l, req);
        log.info(msgTemplate.toString());
        return AppUtils.toNackCore();
    }

    private MsgTemplateEntity queryMsgTemplateEntity(String tpl_key,Long userId, MessageSendReq req) {
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

        if (StringUtils.isEmpty(resultObj.getTemplate())) {
            throw new WebAppException(AppUtils.ERROR_DB_CORE, "消息模板无内容，信息无法发送");
        }
        String tplName = resultObj.getTplName();
        String msgAddr = null;
        if (resultObj.getAuthType() == 0) {
            msgAddr = req.getMsgAddr();
        } else if (resultObj.getAuthType() == 1) {
            msgAddr = req.getMsgAddr();
            if (null == userId || userId <= 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            UserEntity queryUser = new UserEntity();
            queryUser.setId(userId);
            UserEntity resultUser = userMapper.selectByPrimaryKey(queryUser);
            if (null == resultUser || resultUser.getStatus() != AppConfig.DB_STATUS_OK) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在或账户异常", tplName));
            }
        } else if (resultObj.getAuthType() == 2) {
            if (null == userId || userId <= 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            UserEntity queryUser = new UserEntity();
            queryUser.setId(userId);
            UserEntity resultUser = userMapper.selectByPrimaryKey(queryUser);
            if (null == resultUser || resultUser.getStatus() != AppConfig.DB_STATUS_OK) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在或账户异常", tplName));
            }
            msgAddr = validBindMsgAddr(req.getMsgType(), req.getMsgBindAddr(), tplName, resultUser);
        } else if (resultObj.getAuthType() == 3) {
            if (null == userId || userId <= 0) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            UserEntity queryUser = new UserEntity();
            queryUser.setId(userId);
            UserEntity resultUser = userMapper.selectByPrimaryKey(queryUser);
            if (null == resultUser) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不存在", tplName));
            }
            if (resultUser.getStatus() <= 1 || resultUser.getStatus() >= 9) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户不需要解冻，已恢复正常", tplName));
            }
            msgAddr = validBindMsgAddr(req.getMsgType(), req.getMsgBindAddr(), tplName, resultUser);
        }
        if (StringUtils.isEmpty(msgAddr)) {
            // 消息发送失败
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,送达地址为空。", tplName));
        }
        // 开始解析模板的配置
        return resultObj;
    }

    private String validBindMsgAddr(String msgType, String reqMsgAddr, String msgFunction, UserEntity resultUser) {
        String msgAddr = null;
        if (StringUtils.isEquals(msgType, "mobile")) {
            String msgAddrInDb = resultUser.getBindPhone();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定手机号", msgFunction));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的手机号", msgFunction));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "email")) {
            String msgAddrInDb = resultUser.getBindEmail();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定Email", msgFunction));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的Email", msgFunction));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "weixin")) {
            String msgAddrInDb = resultUser.getBindWeixin();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定微信", msgFunction));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的微信", msgFunction));
            }
            msgAddr = msgAddrInDb;
        } else if (StringUtils.isEquals(msgType, "qq")) {
            String msgAddrInDb = resultUser.getBindQq();
            if (StringUtils.isEmpty(msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,用户没有绑定QQ", msgFunction));
            }
            if (StringUtils.isNotEmpty(reqMsgAddr) && !StringUtils.isEquals(reqMsgAddr, msgAddrInDb)) {
                throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的QQ", msgFunction));
            }
            msgAddr = msgAddrInDb;
        }
        if (StringUtils.isEmpty(msgAddr)) {
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("%s发送失败,不是该用户绑定的通讯方式", msgFunction));
        }
        return msgAddr;
    }
}

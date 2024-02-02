package com.ruomm.springcloud.authserver.service;

import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.MessageSendReq;
import com.ruomm.springcloud.authserver.dao.MsgContentMapper;
import com.ruomm.springcloud.authserver.dao.MsgTemplateMapper;
import com.ruomm.springcloud.authserver.dao.UserMapper;
import com.ruomm.springcloud.authserver.entry.MsgTemplateEntity;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 0:54
 */
@Service
public class MessageService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MsgContentMapper msgContentMapper;
    @Autowired
    MsgTemplateMapper msgTemplateMapper;

    public CommonResponse verifyCodeSend(MessageSendReq req) {
        return AppUtils.toNackCore();
    }

    private MsgTemplateEntity msgTemplateMapper(Long templateId) {

        MsgTemplateEntity queryObj = new MsgTemplateEntity();
        queryObj.setId(templateId);
        MsgTemplateEntity resultObj = msgTemplateMapper.selectByPrimaryKey(queryObj);
        if (null == resultObj) {

        }
        return resultObj;
    }
}

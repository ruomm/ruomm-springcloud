package com.ruomm.springcloud.authserver.service;

import com.ruomm.springcloud.authserver.config.AppConfig;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.UserCreateReq;
import com.ruomm.springcloud.authserver.dal.response.UserCreateResp;
import com.ruomm.springcloud.authserver.dao.UserMapper;
import com.ruomm.springcloud.authserver.entry.UserEntity;
import com.ruomm.springcloud.authserver.exception.WebAppException;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import com.ruomm.springcloud.authserver.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/1/30 0:17
 */
@Service
public class UserManagerService {
    @Autowired
    UserMapper userMapper;

    public CommonResponse<UserCreateResp> createUser(UserCreateReq req) {
        //判断用户名称是否重复
        checkUserNameRepeat(req.getUserName());
        checkBindPhoneRepeat(req.getBindPhone());
        String password_slat = AppConfig.TOKEN_HELPER.generateToken(8);
        String password = WebUtils.passwordByClear(req.getPassword(),password_slat);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(req.getUserName());
        userEntity.setNickName(req.getNickName());
        userEntity.setPassword(password);
        userEntity.setPasswordSlat(password_slat);
        userEntity.setUserType(1);
        userEntity.setRoleId(0l);
        userEntity.setStatus(1);
        userEntity.setBindPhone(req.getBindPhone());
        userEntity.setVersion(0);
        Date dateNow = new Date();
        userEntity.setCreatedAt(dateNow);
        userEntity.setUpdatedAt(dateNow);
        int dbResult=userMapper.insertSelective(userEntity);
        if (dbResult!=1) {
            return AppUtils.toNackCore("用户创建失败");
        }
        UserCreateResp resp = new UserCreateResp();
        resp.setUserId(userEntity.getId());
        return AppUtils.toAckT(resp);
    }

    // 判断用户昵称是否重复
    public boolean checkUserNameRepeat(String userName){
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("userName", userName);
        criteria.andNotEqualTo("status", 9);
        int count = userMapper.selectCountByExample(example);
        if (count>0){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("用户名(%s)重复。",userName));
        }
        return true;
    }
    // 判断用户昵称是否重复
    public boolean checkBindPhoneRepeat(String bindPhone){
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("bindPhone", bindPhone);
        criteria.andNotEqualTo("status", 9);
        int count = userMapper.selectCountByExample(example);
        if (count>0){
            throw new WebAppException(AppUtils.ERROR_CORE, String.format("绑定的手机号(%s)重复。",bindPhone));
        }
        return true;
    }
}

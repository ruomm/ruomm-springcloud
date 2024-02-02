package com.ruomm.springcloud.authserver.dal.request;

import com.ruomm.webx.validatorx.validator.CheckAllowStr;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/1 22:01
 */
@Getter
@Setter
@ToString
public class MessageSendReq {
    @NotNull(message = "信息模板ID不能为空，且必须为正整数")
    @Min(value = 1, message = "信息模板ID不能为空，且必须为正整数")
    private Long templateId;
    @CheckAllowStr(allowStr = "mobile,email,weixin,qq", message = "信息类型必须是mobile,email,weixin,qq中的一个")
    private String msgType;
    @Length(max = 64,message = "信息送达地址不能超过64个字符")
    private String msgToAddress;
    @Length(max = 1024, message = "信息关联目标地址不能超过64个字符")
    private String msgBindAddress;
    // template模板参数对应的键值对列表
    private Map<String,String> contentMap;

}

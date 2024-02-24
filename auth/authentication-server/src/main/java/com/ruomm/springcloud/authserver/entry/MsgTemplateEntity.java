package com.ruomm.springcloud.authserver.entry;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * tableName：tbl_msg_template
 * tableRemarks：信息模板表
 * @copyright wanruome-2024
 * @author wanruome
 * @create 2024-02-24 09:01
 *
 * @mbg.generated do_not_delete_during_merge 2024-02-24 09:01:15
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "tbl_msg_template")
public class MsgTemplateEntity {
    /**
     * column: tbl_msg_template.tpl_key, datatype: VARCHAR, length: 64, nullable: false
     * remark: 短信模板主键KEY
     */
    @Id
    @Column( name = "tpl_key" )
    private String tplKey;

    /**
     * column: tbl_msg_template.tpl_name, datatype: VARCHAR, length: 64, nullable: false
     * remark: 短信模板名称
     */
    @Column( name = "tpl_name" )
    private String tplName;

    /**
     * column: tbl_msg_template.bind_tpl_key, datatype: VARCHAR, length: 64, nullable: true
     * remark: 关联短信模板主键KEY。如是有关联主键KEY，则短信发送前需要检查前一条关联短信是否验证通过。
     */
    @Column( name = "bind_tpl_key" )
    private String bindTplKey;

    /**
     * column: tbl_msg_template.template, datatype: VARCHAR, length: 1024, nullable: false
     * remark: 短信模板内容。按照短信模板填充短信内容，通用替换字段：${verifycode},${validtime},${appname},${year},${month},${day},${time},${datetime},${userId},${userName}
     */
    @Column( name = "template" )
    private String template;

    /**
     * column: tbl_msg_template.auth_type, datatype: INTEGER, length: 10, nullable: false
     * remark: 短信发送授权控制。0.不需要授权；1.需要授权；2.需要授权并且是账户绑定的通讯方式；3.需要校验账户；4.需要校验账户并且是账户绑定的通讯方式
     */
    @Column( name = "auth_type" )
    private Integer authType;

    /**
     * column: tbl_msg_template.user_status, datatype: INTEGER, length: 10, nullable: false
     * remark: 发送短信时候的用户状态。1.正常；2.密码过期；3.登录限制；9.停用
     */
    @Column( name = "user_status" )
    private Integer userStatus;

    /**
     * column: tbl_msg_template.auth_uri, datatype: VARCHAR, length: 64, nullable: true
     * remark: 短信授权URI。URI为空则是通用短信，不为空则是特定URI用途的短信。
     */
    @Column( name = "auth_uri" )
    private String authUri;

    /**
     * column: tbl_msg_template.valid_time, datatype: INTEGER, length: 10, nullable: true
     * remark: 验证码有效期(单位秒)
     */
    @Column( name = "valid_time" )
    private Integer validTime;

    /**
     * column: tbl_msg_template.repeat_skip_time, datatype: INTEGER, length: 10, nullable: true
     * remark: 再次发送间隔(单位秒)
     */
    @Column( name = "repeat_skip_time" )
    private Integer repeatSkipTime;

    /**
     * column: tbl_msg_template.limit_by_term, datatype: INTEGER, length: 10, nullable: true
     * remark: 同一设备，一天发送的次数限制
     */
    @Column( name = "limit_by_term" )
    private Integer limitByTerm;

    /**
     * column: tbl_msg_template.limit_by_user, datatype: INTEGER, length: 10, nullable: true
     * remark: 同一用户，一天发送的次数限制
     */
    @Column( name = "limit_by_user" )
    private Integer limitByUser;

    /**
     * column: tbl_msg_template.limit_by_addr, datatype: INTEGER, length: 10, nullable: true
     * remark: 同一发送目标，一天发送的次数限制
     */
    @Column( name = "limit_by_addr" )
    private Integer limitByAddr;

    /**
     * column: tbl_msg_template.status, datatype: INTEGER, length: 10, nullable: false
     * remark: 状态。0.停用；1.启用
     */
    @Column( name = "status" )
    private Integer status;

    /**
     * column: tbl_msg_template.version, datatype: INTEGER, length: 10, nullable: false
     * remark: 乐观锁
     */
    @Column( name = "version" )
    private Integer version;

    /**
     * column: tbl_msg_template.created_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 创建时间
     */
    @Column( name = "created_at" )
    private Date createdAt;

    /**
     * column: tbl_msg_template.updated_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 更新时间
     */
    @Column( name = "updated_at" )
    private Date updatedAt;

    /**
     * column: tbl_msg_template.deleted_at, datatype: TIMESTAMP, length: 26, nullable: true
     * remark: 删除时间
     */
    @Column( name = "deleted_at" )
    private Date deletedAt;
}
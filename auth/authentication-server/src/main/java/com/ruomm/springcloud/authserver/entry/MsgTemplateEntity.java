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
 * @create 2024-02-02 18:51
 *
 * @mbg.generated do_not_delete_during_merge 2024-02-02 18:51:30
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "tbl_msg_template")
public class MsgTemplateEntity {
    /**
     * column: tbl_msg_template.id, datatype: BIGINT, length: 19, nullable: false
     * remark: 主键ID
     */
    @Id
    @Column( name = "id" )
    private Long id;

    /**
     * column: tbl_msg_template.bind_id, datatype: BIGINT, length: 19, nullable: true
     * remark: 关联主键ID。如是有关联主键ID，则短信发送前需要检查前一条关联短信是否验证通过。
     */
    @Column( name = "bind_id" )
    private Long bindId;

    /**
     * column: tbl_msg_template.template, datatype: VARCHAR, length: 16, nullable: false
     * remark: 短信模板。按照短信模板填充短信内容，通用替换字段：${verifycode},${year},${month},${day},${time},${datetime},${userId},${userName}
     */
    @Column( name = "template" )
    private String template;

    /**
     * column: tbl_msg_template.auth_type, datatype: INTEGER, length: 10, nullable: false
     * remark: 短信发送授权控制。0.不需要授权；1.需要授权；2.可授权可不授权
     */
    @Column( name = "auth_type" )
    private Integer authType;

    /**
     * column: tbl_msg_template.auth_uri, datatype: INTEGER, length: 10, nullable: true
     * remark: 短信授权URI。URI为空则是通用短信，不为空则是特定URI用途的短信。
     */
    @Column( name = "auth_uri" )
    private Integer authUri;

    /**
     * column: tbl_msg_template.valid_time, datatype: INTEGER, length: 10, nullable: true
     * remark: 验证码有效期，单位秒
     */
    @Column( name = "valid_time" )
    private Integer validTime;

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
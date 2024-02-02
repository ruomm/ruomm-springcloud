package com.ruomm.springcloud.authserver.entry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * tableName：tbl_msg_content
 * tableRemarks：信息内容表
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
@Table(name = "tbl_msg_content")
public class MsgContentEntity {
    /**
     * column: tbl_msg_content.id, datatype: BIGINT, length: 19, nullable: false
     * remark: 主键ID
     */
    @Id
    @Column( name = "id" )
    private Long id;

    /**
     * column: tbl_msg_content.alias_id, datatype: VARCHAR, length: 64, nullable: false
     * remark: 主键别名。需要返回短信ID时候，建议返回别名ID
     */
    @Column( name = "alias_id" )
    private String aliasId;

    /**
     * column: tbl_msg_content.template_id, datatype: BIGINT, length: 19, nullable: false
     * remark: 信息模板ID
     */
    @Column( name = "template_id" )
    private Long templateId;

    /**
     * column: tbl_msg_content.user_id, datatype: BIGINT, length: 19, nullable: true
     * remark: 用户ID
     */
    @Column( name = "user_id" )
    private Long userId;

    /**
     * column: tbl_msg_content.msg_type, datatype: VARCHAR, length: 16, nullable: false
     * remark: 信息类型，mobile、email、weixin、qq等
     */
    @Column( name = "msg_type" )
    private String msgType;

    /**
     * column: tbl_msg_content.msg_to_address, datatype: VARCHAR, length: 64, nullable: false
     * remark: 信息送达地址，没有user_id时候必须填写
     */
    @Column( name = "msg_to_address" )
    private String msgToAddress;

    /**
     * column: tbl_msg_content.msg_bind_address, datatype: VARCHAR, length: 64, nullable: true
     * remark: 信息关联目标地址。如修改手机号，填写待修改的手机号
     */
    @Column( name = "msg_bind_address" )
    private String msgBindAddress;

    /**
     * column: tbl_msg_content.msg_content, datatype: VARCHAR, length: 1024, nullable: false
     * remark: 信息内容
     */
    @Column( name = "msg_content" )
    private String msgContent;

    /**
     * column: tbl_msg_content.verify_code, datatype: VARCHAR, length: 16, nullable: true
     * remark: 验证码
     */
    @Column( name = "verify_code" )
    private String verifyCode;

    /**
     * column: tbl_msg_content.valid_time, datatype: INTEGER, length: 10, nullable: true
     * remark: 验证码有效期，单位秒
     */
    @Column( name = "valid_time" )
    private Integer validTime;

    /**
     * column: tbl_msg_content.status, datatype: INTEGER, length: 10, nullable: false
     * remark: 状态。1.发送成功；2.验证成功；3.验证失败；9.失效
     */
    @Column( name = "status" )
    private Integer status;

    /**
     * column: tbl_msg_content.version, datatype: INTEGER, length: 10, nullable: false
     * remark: 乐观锁
     */
    @Column( name = "version" )
    private Integer version;

    /**
     * column: tbl_msg_content.created_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 创建时间
     */
    @Column( name = "created_at" )
    private Date createdAt;

    /**
     * column: tbl_msg_content.updated_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 更新时间
     */
    @Column( name = "updated_at" )
    private Date updatedAt;

    /**
     * column: tbl_msg_content.deleted_at, datatype: TIMESTAMP, length: 26, nullable: true
     * remark: 删除时间
     */
    @Column( name = "deleted_at" )
    private Date deletedAt;
}
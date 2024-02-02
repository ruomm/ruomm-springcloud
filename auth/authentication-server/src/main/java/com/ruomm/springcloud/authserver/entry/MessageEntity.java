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
 * tableName：tbl_messages
 * tableRemarks：信息表
 * @copyright wanruome-2024
 * @author wanruome
 * @create 2024-02-02 15:33
 *
 * @mbg.generated do_not_delete_during_merge 2024-02-02 15:33:28
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "tbl_messages")
public class MessageEntity {
    /**
     * column: tbl_messages.id, datatype: BIGINT, length: 19
     * remark: 主键ID
     */
    @Id
    @Column( name = "id" )
    private Long id;

    /**
     * column: tbl_messages.user_id, datatype: BIGINT, length: 19
     * remark: 用户ID
     */
    @Column( name = "user_id" )
    private Long userId;

    /**
     * column: tbl_messages.msg_function_id, datatype: BIGINT, length: 19
     * remark: 功能，不同功能需要不同的权限
     */
    @Column( name = "msg_function_id" )
    private Long msgFunctionId;

    /**
     * column: tbl_messages.msg_type, datatype: VARCHAR, length: 16
     * remark: 信息类型，mobile、email、weixin、qq等
     */
    @Column( name = "msg_type" )
    private String msgType;

    /**
     * column: tbl_messages.msg_dest, datatype: VARCHAR, length: 64
     * remark: 目标，修改验证手段时候需要。
     */
    @Column( name = "msg_dest" )
    private String msgDest;

    /**
     * column: tbl_messages.msg_content, datatype: VARCHAR, length: 1024
     * remark: 信息内容
     */
    @Column( name = "msg_content" )
    private String msgContent;

    /**
     * column: tbl_messages.verify_code, datatype: VARCHAR, length: 16
     * remark: 验证码。
     */
    @Column( name = "verify_code" )
    private String verifyCode;

    /**
     * column: tbl_messages.valid_time, datatype: INTEGER, length: 10
     * remark: 验证码的有效期啊
     */
    @Column( name = "valid_time" )
    private Integer validTime;

    /**
     * column: tbl_messages.msg_status, datatype: INTEGER, length: 10
     * remark: 验证码的状态。1.发送成功；2.验证成功 3.验证失败 9.失效
     */
    @Column( name = "msg_status" )
    private Integer msgStatus;

    /**
     * column: tbl_messages.version, datatype: INTEGER, length: 10
     * remark: 乐观锁
     */
    @Column( name = "version" )
    private Integer version;

    /**
     * column: tbl_messages.created_at, datatype: TIMESTAMP, length: 26
     * remark: 创建时间
     */
    @Column( name = "created_at" )
    private Date createdAt;

    /**
     * column: tbl_messages.updated_at, datatype: TIMESTAMP, length: 26
     * remark: 更新时间
     */
    @Column( name = "updated_at" )
    private Date updatedAt;

    /**
     * column: tbl_messages.deleted_at, datatype: TIMESTAMP, length: 26
     * remark: 删除时间
     */
    @Column( name = "deleted_at" )
    private Date deletedAt;
}
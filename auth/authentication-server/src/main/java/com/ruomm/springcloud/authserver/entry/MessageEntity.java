package com.ruomm.springcloud.authserver.entry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * tableName：tbl_message
 * tableRemarks：无
 * @copyright wanruome-2024
 * @author wanruome
 * @create 2024-02-01 21:59
 *
 * @mbg.generated do_not_delete_during_merge 2024-02-01 21:59:03
 */
@Entity
@Data
@Table(name = "tbl_message")
public class MessageEntity {
    /**
     * column: tbl_message.id, datatype: BIGINT, length: 19
     * remark: 主键ID
     */
    @Id
    @Column( name = "id" )
    private Long id;

    /**
     * column: tbl_message.user_id, datatype: BIGINT, length: 19
     * remark: 用户ID
     */
    @Column( name = "user_id" )
    private Long userId;

    /**
     * column: tbl_message.msg_function_id, datatype: BIGINT, length: 19
     * remark: 功能，不同功能需要不同的权限
     */
    @Column( name = "msg_function_id" )
    private Long msgFunctionId;

    /**
     * column: tbl_message.msg_type, datatype: VARCHAR, length: 16
     * remark: 类型，短信、手机、微信、QQ等...
     */
    @Column( name = "msg_type" )
    private String msgType;

    /**
     * column: tbl_message.msg_dest, datatype: VARCHAR, length: 64
     * remark: 目标，修改验证手段时候需要。
     */
    @Column( name = "msg_dest" )
    private String msgDest;

    /**
     * column: tbl_message.msg_content, datatype: VARCHAR, length: 1024
     * remark: 信息内容
     */
    @Column( name = "msg_content" )
    private String msgContent;

    /**
     * column: tbl_message.verify_code, datatype: VARCHAR, length: 16
     * remark: 验证码。
     */
    @Column( name = "verify_code" )
    private String verifyCode;

    /**
     * column: tbl_message.valid_time, datatype: INTEGER, length: 10
     * remark: 验证码的有效期啊
     */
    @Column( name = "valid_time" )
    private Integer validTime;

    /**
     * column: tbl_message.msg_status, datatype: INTEGER, length: 10
     * remark: 验证码的状态。1.发送成功；2.验证成功 3.验证失败 9.失效
     */
    @Column( name = "msg_status" )
    private Integer msgStatus;

    /**
     * column: tbl_message.created_at, datatype: TIMESTAMP, length: 26
     * remark: 创建时间
     */
    @Column( name = "created_at" )
    private Date createdAt;

    /**
     * column: tbl_message.updated_at, datatype: TIMESTAMP, length: 26
     * remark: 更新时间
     */
    @Column( name = "updated_at" )
    private Date updatedAt;

    /**
     * column: tbl_message.deleted_at, datatype: TIMESTAMP, length: 26
     * remark: 删除时间
     */
    @Column( name = "deleted_at" )
    private Date deletedAt;
}
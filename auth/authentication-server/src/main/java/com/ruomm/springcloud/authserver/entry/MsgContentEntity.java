package com.ruomm.springcloud.authserver.entry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.Version;

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
 * @create 2024-02-24 10:36
 *
 * @mbg.generated do_not_delete_during_merge 2024-02-24 10:36:19
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
     * column: tbl_msg_content.tpl_key, datatype: VARCHAR, length: 64, nullable: false
     * remark: 短信模板主键KEY
     */
    @Column( name = "tpl_key" )
    private String tplKey;

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
     * column: tbl_msg_content.msg_addr, datatype: VARCHAR, length: 64, nullable: false
     * remark: 信息送达地址，没有user_id时候必须填写
     */
    @Column( name = "msg_addr" )
    private String msgAddr;

    /**
     * column: tbl_msg_content.msg_bind_addr, datatype: VARCHAR, length: 64, nullable: true
     * remark: 信息关联目标地址。如修改手机号，填写待修改的手机号
     */
    @Column( name = "msg_bind_addr" )
    private String msgBindAddr;

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
     * remark: 验证码有效期(单位秒)
     */
    @Column( name = "valid_time" )
    private Integer validTime;

    /**
     * column: tbl_msg_content.check_count, datatype: INTEGER, length: 10, nullable: false
     * remark: 验证码已核验次数，超过一定核验次数验证码失效
     */
    @Column( name = "check_count" )
    private Integer checkCount;

    /**
     * column: tbl_msg_content.status, datatype: INTEGER, length: 10, nullable: false
     * remark: 状态。1.发送成功；2.验证成功；3.验证失败；9.失效
     */
    @Column( name = "status" )
    private Integer status;

    /**
     * column: tbl_msg_content.client_id, datatype: VARCHAR, length: 64, nullable: true
     * remark: 客户端编号
     */
    @Column( name = "client_id" )
    private String clientId;

    /**
     * column: tbl_msg_content.client_host, datatype: VARCHAR, length: 64, nullable: true
     * remark: 客户端host地址
     */
    @Column( name = "client_host" )
    private String clientHost;

    /**
     * column: tbl_msg_content.client_ip, datatype: VARCHAR, length: 64, nullable: true
     * remark: 客户端IP地址
     */
    @Column( name = "client_ip" )
    private String clientIp;

    /**
     * column: tbl_msg_content.version, datatype: INTEGER, length: 10, nullable: false
     * remark: 乐观锁
     */
    @Version
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
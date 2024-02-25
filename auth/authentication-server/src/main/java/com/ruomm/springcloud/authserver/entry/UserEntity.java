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
 * tableName：tbl_users
 * tableRemarks：用户表
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
@Table(name = "tbl_users")
public class UserEntity {
    /**
     * column: tbl_users.id, datatype: BIGINT, length: 19, nullable: false
     * remark: 主键ID
     */
    @Id
    @Column( name = "id" )
    private Long id;

    /**
     * column: tbl_users.user_name, datatype: VARCHAR, length: 32, nullable: true
     * remark: 用户名称
     */
    @Column( name = "user_name" )
    private String userName;

    /**
     * column: tbl_users.nick_name, datatype: VARCHAR, length: 32, nullable: true
     * remark: 用户昵称
     */
    @Column( name = "nick_name" )
    private String nickName;

    /**
     * column: tbl_users.user_type, datatype: INTEGER, length: 10, nullable: false
     * remark: 用户类型
     */
    @Column( name = "user_type" )
    private Integer userType;

    /**
     * column: tbl_users.role_id, datatype: BIGINT, length: 19, nullable: false
     * remark: 角色ID
     */
    @Column( name = "role_id" )
    private Long roleId;

    /**
     * column: tbl_users.password, datatype: VARCHAR, length: 128, nullable: false
     * remark: 密码
     */
    @Column( name = "password" )
    private String password;

    /**
     * column: tbl_users.password_slat, datatype: VARCHAR, length: 32, nullable: false
     * remark: 密码BE盐值
     */
    @Column( name = "password_slat" )
    private String passwordSlat;

    /**
     * column: tbl_users.status, datatype: INTEGER, length: 10, nullable: false
     * remark: 用户状态。1.正常；2.密码过期；3.登录限制；9.停用
     */
    @Column( name = "status" )
    private Integer status;

    /**
     * column: tbl_users.bind_weixin, datatype: VARCHAR, length: 64, nullable: true
     * remark: 绑定的微信号
     */
    @Column( name = "bind_weixin" )
    private String bindWeixin;

    /**
     * column: tbl_users.bind_qq, datatype: VARCHAR, length: 16, nullable: true
     * remark: 绑定的QQ号
     */
    @Column( name = "bind_qq" )
    private String bindQq;

    /**
     * column: tbl_users.bind_phone, datatype: VARCHAR, length: 20, nullable: true
     * remark: 绑定的手机号
     */
    @Column( name = "bind_phone" )
    private String bindPhone;

    /**
     * column: tbl_users.bind_email, datatype: VARCHAR, length: 64, nullable: true
     * remark: 绑定的Email
     */
    @Column( name = "bind_email" )
    private String bindEmail;

    /**
     * column: tbl_users.contact_phone, datatype: VARCHAR, length: 20, nullable: true
     * remark: 联系手机号
     */
    @Column( name = "contact_phone" )
    private String contactPhone;

    /**
     * column: tbl_users.contact_email, datatype: VARCHAR, length: 64, nullable: true
     * remark: 联系Email
     */
    @Column( name = "contact_email" )
    private String contactEmail;

    /**
     * column: tbl_users.version, datatype: INTEGER, length: 10, nullable: false
     * remark: 乐观锁
     */
    @Column( name = "version" )
    private Integer version;

    /**
     * column: tbl_users.created_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 创建时间
     */
    @Column( name = "created_at" )
    private Date createdAt;

    /**
     * column: tbl_users.updated_at, datatype: TIMESTAMP, length: 26, nullable: false
     * remark: 更新时间
     */
    @Column( name = "updated_at" )
    private Date updatedAt;

    /**
     * column: tbl_users.deleted_at, datatype: TIMESTAMP, length: 26, nullable: true
     * remark: 删除时间
     */
    @Column( name = "deleted_at" )
    private Date deletedAt;
}
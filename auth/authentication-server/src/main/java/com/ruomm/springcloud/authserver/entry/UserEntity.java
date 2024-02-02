package com.ruomm.springcloud.authserver.entry;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/1/29 21:19
 */
@Entity
@Data
@Table(name = "tbl_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "user_name",length = 32)
    private String userName;
    @Column(name = "nick_name",length = 32)
    private String nickName;
    @Column(name = "user_type",nullable = false)
    private Integer userType;
    @Column(name = "rold_id",nullable = false)
    private Long roleId;
    @Column(name = "password",length = 128,nullable = false)
    private String password;
    @Column(name = "password_slat",length = 32,nullable = false)
    private String passwordSlat;
    @Column(name = "user_status",nullable = false)
    private Integer userStatus;
    @Column(name = "bind_weixin",length = 64)
    private String bindWeixin;
    @Column(name = "bind_qq",length = 16)
    private String bindQQ;
    @Column(name = "bind_phone",length = 20)
    private String bindPhone;
    @Column(name = "bind_email",length = 64)
    private String bindEmail;
    @Column(name = "contact_phone",length = 20)
    private String contactPhone;
    @Column(name = "contact_email",length = 64)
    private String contactEmail;
    @Column(name = "version",nullable = false)
    private Integer version;
    @Column(name = "created_at",nullable = false)
    private Date createdAt;
    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;
    @Column(name = "deleted_at")
    private Date deletedAt;

}

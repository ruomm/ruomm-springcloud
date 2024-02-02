package com.ruomm.springcloud.authserver.entry;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tableName：ruomm_common_config
 * tableRemarks：无
 *
 * @author wanruome
 * @copyright wanruome-2022
 * @create 2022-04-11 15:04
 * @mbg.generated do_not_delete_during_merge 2022-04-11 15:04:08
 */
@Entity
@Data
@Table(name = "ruomm_common_config")
@IdClass(RuommCommonConfigId.class)
public class RuommCommonConfig extends RuommCommonConfigId implements Serializable {
    private static final long serialVersionUID = 18L;

    /**
     * column: ruomm_common_config.config_key, datatype: VARCHAR, length: 64
     * remark: 配置主键key
     */
    @Id
    @Column(name = "config_key",length = 64)
    private String configKey;

    /**
     * column: ruomm_common_config.config_env, datatype: VARCHAR, length: 64
     * remark: 配置主键env
     */
    @Id
    @Column(name = "config_env",length = 64)
    private String configEnv;

//    @Column(name = "config_env",length = 64)
//    @Transient
//    private RuommCommonConfigId configId;

    /**
     * column: ruomm_common_config.config_val, datatype: VARCHAR, length: 4000
     * remark: 配置值
     */
    @Column(name = "config_val",length = 4000)
    private String configVal;

    /**
     * column: ruomm_common_config.config_remark, datatype: VARCHAR, length: 256
     * remark: 备注
     */
    @Column(name = "config_remark",length = 256)
    private String configRemark;

    /**
     * column: ruomm_common_config.config_status, datatype: INTEGER, length: 10
     * remark: 状态 1.正常 0.停用 其他.未知状态
     */
    @Column(name = "config_status",length = 10)
    private Integer configStatus;

    /**
     * column: ruomm_common_config.create_datetime, datatype: TIMESTAMP, length: 19
     * remark: 创建时间
     */
    @Column(name = "create_datetime")
    private Date createDatetime;

    /**
     * column: ruomm_common_config.update_datetime, datatype: TIMESTAMP, length: 19
     * remark: 修改时间
     */
    @Column(name = "update_datetime")
    private Date updateDatetime;
}
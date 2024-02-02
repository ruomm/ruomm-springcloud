package com.ruomm.springcloud.authserver.entry;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * tableName：ruomm_common_config
 * tableRemarks：无
 *
 * @author wanruome
 * @copyright wanruome-2022
 * @create 2022-04-11 15:04
 * @mbg.generated do_not_delete_during_merge 2022-04-11 15:04:08
 */
@Data
//@IdClass(RuommCommonConfigId.class)
//@Embeddable
public class RuommCommonConfigId implements Serializable {
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

    public RuommCommonConfigId() {

    }
    public RuommCommonConfigId(String configKey, String configEnv) {
        this.configKey = configKey;
        this.configEnv = configEnv;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuommCommonConfigId that = (RuommCommonConfigId) o;
        return Objects.equals(configKey, that.configKey) && Objects.equals(configEnv, that.configEnv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configKey, configEnv);
    }
}
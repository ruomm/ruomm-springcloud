package com.ruomm.springcloud.authserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 牛牛-收单事业部-www.ruomm.com
 * @version 1.0
 * @copyright 新生支付-www.hnapay.com
 * @create 2021/11/11 14:03
 */
//@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Getter
@Setter
@ToString
public class DruidDataSourceProperties {
    // jdbc
    @Value(value = "${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.username}")
    private String username;
    @Value(value = "${spring.datasource.password}")
    private String password;
    // jdbc connection pool
    private int initialSize;
    private int minIdle;
    private int maxActive = 100;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private int validationQueryTimeout;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private boolean useGlobalDataSourceStat;
    private String connectionProperties;
    // filter
    private String filters;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.allow}")
    private String statAllow;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.deny}")
    private String statDeny;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.enabled}")
    private boolean statEnabled;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.reset-enable}")
    private boolean statResetEnable;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.login-username}")
    private String statLoginUsername;
    @Value(value = "${spring.datasource.druid.stat-view-servlet.login-password}")
    private String statLoginPassword;
}

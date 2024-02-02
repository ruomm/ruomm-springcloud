package com.ruomm.springcloud.authserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author 牛牛-收单事业部-www.ruomm.com
 * @version 1.0
 * @copyright 新生支付-www.hnapay.com
 * @create 2021/11/11 14:06
 */
@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DruidConfig {
    @Autowired
    private DruidDataSourceProperties properties;

    // 开始读取外部配置文件
    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource() {
//        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        String profile = wac.getEnvironment().getActiveProfiles()[0];

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
//        druidDataSource.setValidationQueryTimeout(properties.getValidationQueryTimeout());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setUseGlobalDataSourceStat(properties.isUseGlobalDataSourceStat());
        druidDataSource.setConnectionProperties(properties.getConnectionProperties());
        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return druidDataSource;
    }

    /**
     * 注册Servlet信息， 配置监控视图
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");

        //白名单：
        servletRegistrationBean.addInitParameter("allow", properties.getStatAllow());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", properties.getStatDeny());
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("enabled", String.valueOf(properties.isStatEnabled()));
//        if (StringUtils.isEqualsIgnoreCase(properties.getStatEnabled(),"true") || StringUtils.isEqualsIgnoreCase(properties.getStatEnabled(),"false")) {
//            servletRegistrationBean.addInitParameter("enabled", properties.getStatEnabled().toLowerCase());
//        }

        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", String.valueOf(properties.isStatResetEnable()));
//        if (StringUtils.isEqualsIgnoreCase(properties.getStatResetEnable(),"true")|| StringUtils.isEqualsIgnoreCase(properties.getStatResetEnable(),"false")) {
//            servletRegistrationBean.addInitParameter("resetEnable", properties.getStatResetEnable().toLowerCase());
//        }
        //登录查看信息的账号密码, 用于登录Druid监控后台
        servletRegistrationBean.addInitParameter("loginUsername", properties.getStatLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", properties.getStatLoginPassword());
        return servletRegistrationBean;

    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<jakarta.servlet.Filter> filterRegistrationBean() {
        FilterRegistrationBean<jakarta.servlet.Filter> filterRegistrationBean = new FilterRegistrationBean<jakarta.servlet.Filter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}

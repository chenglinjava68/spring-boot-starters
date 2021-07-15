package com.digitalgd.dpd.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author laopan
 * @className EsAuthConfig
 * @date 2021/4/19 17:14
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.auth")
public class EsAuthConfig {
    private boolean enable;
    private String userName;
    private String password;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.digitalgd.dpd.elasticsearch.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * es配置信息
 *
 * @author laopan
 * @className EsConfig
 * @date 2021/3/9 18:37
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class EsConfig {

    private String host;

    private int port;

    private String domainPathPrefix;
    /**
     * 连接超时时间
     */
    private int connectTimeOut;
    /**
     * 读取数据超时时间
     */
    private int socketTimeOut;
    /**
     * 获取连接的超时时间
     */
    private int connectionRequestTimeOut;
    /**
     * 最大连接数
     */
    private int maxConnectNum;
    /**
     * 最大路由连接数
     */
    private int maxConnectPerRoute;
    /**
     * 保持时间
     */
    private int keepAliveTime;

    private String clusterAddress;

    private EsAuthConfig esAuthConfig;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getConnectionRequestTimeOut() {
        return connectionRequestTimeOut;
    }

    public void setConnectionRequestTimeOut(int connectionRequestTimeOut) {
        this.connectionRequestTimeOut = connectionRequestTimeOut;
    }

    public int getMaxConnectNum() {
        return maxConnectNum;
    }

    public void setMaxConnectNum(int maxConnectNum) {
        this.maxConnectNum = maxConnectNum;
    }

    public int getMaxConnectPerRoute() {
        return maxConnectPerRoute;
    }

    public void setMaxConnectPerRoute(int maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getClusterAddress() {
        return clusterAddress;
    }

    public void setClusterAddress(String clusterAddress) {
        this.clusterAddress = clusterAddress;
    }

    public EsAuthConfig getEsAuthConfig() {
        return esAuthConfig;
    }

    public String getDomainPathPrefix() {
        return domainPathPrefix;
    }

    public void setDomainPathPrefix(String domainPathPrefix) {
        this.domainPathPrefix = domainPathPrefix;
    }

    public void setEsAuthConfig(EsAuthConfig esAuthConfig) {
        this.esAuthConfig = esAuthConfig;
    }

}

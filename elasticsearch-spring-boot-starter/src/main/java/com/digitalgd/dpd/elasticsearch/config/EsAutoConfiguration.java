package com.digitalgd.dpd.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laopan
 * @date 2021/4/15 0015 23:05
 */
@Configuration
@EnableConfigurationProperties(EsConfig.class)
@ConditionalOnProperty(prefix = "elasticsearch", name = "enable", havingValue = "true")
public class EsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(EsAutoConfiguration.class);
    @Autowired
    private EsConfig esConfig;

    private static List<HttpHost> hostList = new ArrayList<>();
    private static final String SCHEMA = "http";

    @PostConstruct
    public void init() {
        String clusterAddress = esConfig.getClusterAddress();
        if (null != clusterAddress && clusterAddress.trim().length() > 0) {
            String[] hosts = clusterAddress.split(",");
            for (String host : hosts) {
                try {
                    String[] address = host.split(":");
                    hostList.add(new HttpHost(address[0], Integer.parseInt(address[1]), SCHEMA));
                } catch (Exception e) {
                    log.error("设置集群地址错误：", e);
                }
            }
        } else {
            HttpHost httpHost = new HttpHost(esConfig.getHost(), esConfig.getPort(), SCHEMA);
            hostList.add(httpHost);
        }
    }

    @Bean("restClientBuilder")
    @Primary
    public RestClientBuilder restClientBuilder() {
        int size = hostList.size();
        HttpHost[] hosts = hostList.toArray(new HttpHost[size]);
        return RestClient.builder(HttpHost.create(esConfig.getHost())).setPathPrefix(esConfig.getDomainPathPrefix());
    }

    /**
     * 异步httpclient的连接延时配置
     *
     * @param builder
     */
    private void setConnectTimeOutConfig(RestClientBuilder builder) {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esConfig.getConnectTimeOut())
                    .setSocketTimeout(esConfig.getSocketTimeOut())
                    .setConnectionRequestTimeout(esConfig.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });
    }

    /**
     * 异步httpclient连接数配置
     *
     * @param builder
     */
    private void setConnectConfig(RestClientBuilder builder) {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(esConfig.getMaxConnectNum())
                    .setMaxConnPerRoute(esConfig.getMaxConnectPerRoute())
                    .setKeepAliveStrategy(((httpResponse, httpContext) -> esConfig.getKeepAliveTime()));
            return httpClientBuilder;
        });
    }

    private void setAuth(RestClientBuilder builder) {
        EsAuthConfig esAuthConfig = esConfig.getEsAuthConfig();
        if (null != esAuthConfig && esAuthConfig.isEnable()) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(esAuthConfig.getUserName(), esAuthConfig.getPassword()));
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching().setDefaultCredentialsProvider(credentialsProvider);
                return httpClientBuilder;
            });
        }
    }

    @Bean
    public RestHighLevelClient highLevelClient(@Autowired @Qualifier("restClientBuilder") RestClientBuilder restClientBuilder) {
        // 异步httpclient连接延时配置
        setConnectTimeOutConfig(restClientBuilder);
        // 异步httpclient连接数配置
        setConnectConfig(restClientBuilder);
        setAuth(restClientBuilder);
        return new RestHighLevelClient(restClientBuilder);
    }

    public static void main(String[] args) {
        RestClientBuilder restClientBuilder = RestClient.builder(HttpHost.create("xtbgzww.digitalgd.com.cn")).setPathPrefix("/es/search");;
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "ap20pOPS20"));
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.disableAuthCaching().setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });

        // 异步httpclient连接延时配置
//        setConnectTimeOutConfig(restClientBuilder);
        // 异步httpclient连接数配置
//        setConnectConfig(restClientBuilder);
//        setAuth(restClientBuilder);
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
    }
//    @Bean
//    public IndexOperation esIndexOperation(@Autowired RestHighLevelClient restHighLevelClient) {
//        return new IndexOperation(restHighLevelClient);
//    }
//
//    @Bean
//    public DocOperation esDocOperation(@Autowired RestHighLevelClient restHighLevelClient) {
//        return new DocOperation(restHighLevelClient);
//    }
//    @Bean
//    public GeoQuery geoQuery(@Autowired RestHighLevelClient restHighLevelClient) {
//        return new GeoQuery(restHighLevelClient);
//    }
//    @Bean
//    public Query query(@Autowired RestHighLevelClient restHighLevelClient) {
//        return new Query(restHighLevelClient);
//    }
}

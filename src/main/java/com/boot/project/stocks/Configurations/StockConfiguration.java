package com.boot.project.stocks.Configurations;


import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.HashMap;
import java.util.Map;



@Configuration
@PropertySource("classpath:Stocks.properties")
@ConfigurationProperties(prefix = "my")
public class StockConfiguration {

    private final Map<String, String> companyNames = new HashMap<>();

    public Map<String, String> getCompanyNames (){
        return companyNames;
    }


    @Bean
    public WebClient createWebClient () throws SSLException{
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager( InsecureTrustManagerFactory.INSTANCE )
                .build();
        HttpClient httpConnector = HttpClient.create().secure( sslContextSpec -> sslContextSpec.sslContext( sslContext ) );
        return WebClient.builder().clientConnector( new ReactorClientHttpConnector( httpConnector ) ).build();
    }
}



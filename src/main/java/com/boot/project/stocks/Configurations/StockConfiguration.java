package com.boot.project.stocks.Configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;


@Configuration
@PropertySource("classpath:Stocks.properties")
@ConfigurationProperties(prefix = "my")
public class StockConfiguration {

    private final Map<String, String> companyMap = new HashMap<> ();

    public Map<String, String> getCompanyMap () {
        return companyMap;
    }


}



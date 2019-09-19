package com.boot.project.stocks.Services;

import com.boot.project.stocks.Configurations.StockConfiguration;
import com.boot.project.stocks.Models.StockDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class StockService {

    private static Logger log = LoggerFactory.getLogger ( StockService.class );

    @Autowired
    private WebClient webClient;

    private StockConfiguration stockConfiguration;
    private Map<String, String> companyTickerMap;
    @Value("${iex.api.key}")
    private String IEX_API_KEY;
    @Value("${yahoo.finance.url}")
    private String YAHOO_URL;
    @Value("${iex.cloud.url}")
    private String IEX_URL;

    public StockService (StockConfiguration stockConfiguration){
        this.stockConfiguration = stockConfiguration;
    }

    @PostConstruct
    public void saveAllTickerSymbols (){
        companyTickerMap = new LinkedHashMap<>();
        for (String companyName : stockConfiguration.getCompanyNames().keySet()) {
            String tickerSymbol = generateTickerFromJSON( companyName );
            if(tickerSymbol != null){
                companyTickerMap.put( companyName, tickerSymbol );
                log.info( "Generated Ticker Symbol : {}", tickerSymbol );
            }
        }
    }


    private String generateTickerFromJSON (String stock){

        String url = YAHOO_URL+stock+"&region=1&lang=en";
        String tickerSymbol = null;
        try {
            String json = webClient.get().uri( url ).retrieve().bodyToMono( String.class ).block();
            JSONObject jsonObject = new JSONObject( json );
            int objectIncrementer = 0;
            JSONArray jsonArray = jsonObject.getJSONObject( "ResultSet" ).getJSONArray( "Result" );
            if(jsonArray.length() != 0){
                tickerSymbol = String.valueOf( jsonArray.getJSONObject( objectIncrementer ).get( "symbol" ) );
                while (tickerSymbol.contains( "." )) {
                    tickerSymbol = String.valueOf( jsonArray.getJSONObject( ++objectIncrementer ).get( "symbol" ) );
                }
            }
        } catch (RestClientException | JSONException e) {
            log.info( "Unable to generate ticker symbol for company name  {}", stock );
        }
        return tickerSymbol;
    }


    public StockDetails fetchStockInfo (String stockName){
        String stockSymbol = companyTickerMap != null ? companyTickerMap.get( stockName ) : null;
        StockDetails stockDetails = null;
        try {
            if (stockSymbol != null) {
                String restUrl = IEX_URL+stockSymbol+"/quote?token="+IEX_API_KEY;
                stockDetails = webClient.get().uri( restUrl ).retrieve().bodyToMono( StockDetails.class ).block();
                log.info( stockDetails.toString() );
                return stockDetails;
            }
        } catch (RestClientException | WebClientResponseException res) {
            System.out.println( "Response error exception by company name : "+stockName+" "+res.getMessage() );
        }
        return stockDetails;
    }


}





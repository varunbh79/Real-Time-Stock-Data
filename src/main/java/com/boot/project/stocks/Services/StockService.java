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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class StockService {

    private static Logger log = LoggerFactory.getLogger ( StockService.class );

    @Autowired
    private WebClient webClient;

    @Autowired
    private StockConfiguration stockConfiguration;

    private Map<String, String> companyTickerMap;


    @Value("${iex.api.key}")
    private String IEX_API_KEY;

    @Value("${alpha.vantage.api.key}")
    private String ALPHA_API_KEY;

    @Value("${alpha.vantage.url}")
    private String ALPHA_URL;

    @Value("${iex.cloud.url}")
    private String IEX_URL;

    @PostConstruct
    @Async
    public void saveAllTickerSymbols (){
        companyTickerMap = new LinkedHashMap<>();
        for (String companyName : stockConfiguration.getCompanyNames().keySet()) {
            String tickerSymbol = getStockSymbol( companyName );
            companyTickerMap.put( companyName, getStockSymbol( tickerSymbol ) );
            log.info( "Generated Ticker Symbol : {}", tickerSymbol );
        }
    }


    private String getStockSymbol (String stock){
        String ticker_symbol = null;
        String result = webClient.get().uri( ALPHA_URL+stock+"&apikey="+ALPHA_API_KEY )
                .retrieve().bodyToMono( String.class ).
                        block().replaceAll( "\n", "" );
        JSONObject jsonObject = new JSONObject( result );
        try {
            JSONArray jsonArray = jsonObject.getJSONArray( "bestMatches" );
            if(jsonArray.length() != 0){
                ticker_symbol = String.valueOf( jsonArray.getJSONObject( 0 ).get( "1. symbol" ) );
            }
        } catch (JSONException e) {
            log.info( "Unable to generate Ticker Symmbol : {}", stock+e.getLocalizedMessage() );
        }
        return ticker_symbol;
    }

    public StockDetails fetchStockInfo (String stockName){
        StockDetails stockDetails = null;
        String stockSymbol = companyTickerMap != null ? companyTickerMap.get( stockName ) : null;
        try {
            if (stockSymbol != null) {
                String restUrl = IEX_URL+stockSymbol+"/quote?token="+IEX_API_KEY;
                stockDetails = webClient.get().uri( restUrl ).retrieve().bodyToMono( StockDetails.class ).block();
                stockDetails.setCompanyName ( stockName );
                log.info( stockDetails.toString() );
            }
        } catch (RestClientException res) {
            System.out.println( res.getLocalizedMessage()+res.getMessage() );
        }
        return stockDetails;
    }


}





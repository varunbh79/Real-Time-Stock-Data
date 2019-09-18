package com.boot.project.stocks.Services;

import com.boot.project.stocks.Models.StockDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class StockService {

    private static Logger log = LoggerFactory.getLogger ( StockService.class );

    private RestTemplate restTemplate;

    private String stockSymbol = null;
    @Value("${iex.api-key}")
    private String API_KEY;


    public StockService (RestTemplateBuilder builder) {
        this.restTemplate = builder.build ();
    }

    public String generateTickerFromJSON (String stock) {
        String url = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=" + stock + "&region=1&lang=en";
        String json = "";
        try {
            json = restTemplate.getForObject ( url, String.class );
        } catch (RestClientException e) {
            log.info ( "Unable to generate ticker symbol : {}", e.getCause () );
            return null;
        }
        JSONObject jsonObject = new JSONObject ( json );
        String pj = jsonObject.get ( "ResultSet" ).toString ();
        JSONObject jsonObject1 = new JSONObject ( pj );
        int length = jsonObject1.get ( "Result" ).toString ().length ();
        if (length != 2) {
            String rj = jsonObject1.get ( "Result" ).toString ();
            JSONArray jsonArray = new JSONArray ( rj );
            String res = jsonArray.get ( 0 ).toString ();
            JSONObject finalres = new JSONObject ( res );
            stockSymbol = finalres.get ( "symbol" ).toString ();
        }
        return stock;
    }

    public StockDetails fetchStockInfo (String stockName) {
        log.info ( "API key : {}", API_KEY );
        StockDetails stockDetails = null;
        try {
            if (stockSymbol != null) {
                String restUrl = "https://cloud.iexapis.com/beta/stock/" + stockSymbol + "/quote?token=" + API_KEY;
                String restApiResult = restTemplate.getForObject ( restUrl, String.class );
                stockDetails = restTemplate.getForObject ( restUrl, StockDetails.class );
                stockDetails.setCompanyName ( stockName );
                log.info ( restApiResult );
            }
        } catch (RestClientException res) {
            System.out.println ( res.getLocalizedMessage () );
        }
        return stockDetails;
    }


}





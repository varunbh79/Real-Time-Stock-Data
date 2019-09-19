package com.boot.project.stocks.Controllers;

import com.boot.project.stocks.Configurations.StockConfiguration;
import com.boot.project.stocks.Models.StockDetails;
import com.boot.project.stocks.Services.StockService;
import io.reactivex.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StockRestController {

    private StockService stockService;
    private StockConfiguration stockConfiguration;

    public StockRestController (StockService stockService, StockConfiguration stockConfiguration) {
        this.stockService = stockService;
        this.stockConfiguration = stockConfiguration;
    }

    @GetMapping("/hello")
    public String sayHello (){
        return "Welcome to Spring Boot";
    }


    @GetMapping("/getObservableStockQuotes")
    public Observable<Object> getObservable (){
        Map<String, String> companyNames = stockConfiguration.getCompanyNames();
        if(companyNames == null && companyNames.isEmpty())
            return Observable.just( "Company names cannot be empty" );

        return Observable.create( emitter -> {
            while (!emitter.isDisposed ()) {
                for (String companyName : companyNames.keySet()) {
                    StockDetails stockDetails = stockService.fetchStockInfo( companyName );
                    if (stockDetails != null) {
                        emitter.onNext ( stockDetails );
                    } else
                        emitter.onNext( "Stock Details not found for "+companyName );
                }
            }
        } );
    }

}

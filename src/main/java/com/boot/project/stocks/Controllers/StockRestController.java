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
    private volatile Integer stockCount = new Integer ( 0 );

    public StockRestController (StockService stockService, StockConfiguration stockConfiguration) {
        this.stockService = stockService;
        this.stockConfiguration = stockConfiguration;
    }

    @GetMapping("/getObservableStockQuotes")
    public Observable<StockDetails> getObservable () {
        Map<String, String> companyNames = stockConfiguration.getCompanyMap ();

        return Observable.create ( emitter -> {
            while (!emitter.isDisposed ()) {
                if (stockCount.intValue () == 23) {
                    emitter.onComplete ();
                    break;
                }
                for (String stockSymbol : companyNames.keySet ()) {

                    String name = stockSymbol != null ? stockService.generateTickerFromJSON ( companyNames.get ( stockSymbol ) ) : null;
                    StockDetails stockDetails = name != null ? stockService.fetchStockInfo ( name ) : null;
                    if (stockDetails != null) {
                        incrementStockCount ();
                        emitter.onNext ( stockDetails );
                    }
                }
                // ThreadUtilty.sleep(10);
            }
        } );
    }

    private void incrementStockCount () {
        stockCount = stockCount + 1;
    }
}

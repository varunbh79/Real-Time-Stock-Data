package com.boot.project.stocks.Services;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.time.Instant;

@Service
public class RabbitMqServiceProvider {

    private final String MQ_URL = "http://localhost:15672/api/queues/";
    private final String username = "guest";
    private final String password = "guest";

    @Autowired
    private RestTemplate template;
    private WebClient webClient;

    public RabbitMqServiceProvider() {
        this.webClient = WebClient.create ();
    }


    private String getEncodedInfo() {
        String login = username + ":" + password;
        String encoded = DatatypeConverter.printBase64Binary ( login.getBytes () );
        return encoded;
    }


    public String[] retrieveQueuesFromRabbitMQByTemplate () {
        Instant start = Instant.now ();
        String encoded = getEncodedInfo ();
        RequestCallback requestCallback = request-> request.getHeaders ().set( "Authorization", "Basic " + encoded );
        ResponseExtractor<String> extractor = new HttpMessageConverterExtractor<String> ( String.class, template.getMessageConverters () );
        String result = template.execute ( MQ_URL, HttpMethod.GET,requestCallback,extractor );
        JSONArray objectArray = new JSONArray (result);
        String[] queueNames = new String[objectArray.length ()];
        for (int i = 0; i < objectArray.length (); i++) {
            queueNames[i] = (String) objectArray.getJSONObject ( i ).get ( "name" );
        }
        Instant finish = Instant.now ();
        System.out.println ("Time Taken by Template : " + Duration.between (start,finish).toNanos ());
        return queueNames;
    }

    public String[] retrieveQueueNamesAsync() {

        Instant start = Instant.now ();
        String encoded = getEncodedInfo ();
        String stringMono = webClient.get().uri ( MQ_URL ).header ("Authorization", "Basic " + encoded).retrieve ().bodyToMono (String.class).block ();
        JSONArray objectArray = new JSONArray (stringMono);
        String[] queueNames = new String[objectArray.length ()];
        for (int i = 0; i < objectArray.length (); i++) {
            queueNames[i] = (String) objectArray.getJSONObject ( i ).get ( "name" );
        }
        Instant finish = Instant.now ();
        System.out.println ("Time Taken by Async Mono is : " + Duration.between (start,finish).toNanos ());
        return queueNames;
    }





}

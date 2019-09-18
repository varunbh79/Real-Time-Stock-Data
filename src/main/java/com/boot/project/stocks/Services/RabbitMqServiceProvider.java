package com.boot.project.stocks.Services;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.xml.bind.DatatypeConverter;


@Service
public class RabbitMqServiceProvider {

    @Value("${app.rabbit.mq.url}")
    private String MQ_URL;

    @Value("${app.rabbit.mq.username}")
    private String username;

    @Value("${app.rabbit.mq.password}")
    private String password;

    private RestTemplate template;

    private WebClient webClient;

    public RabbitMqServiceProvider (RestTemplateBuilder builder) {
        this.template = builder.build ();
        this.webClient = WebClient.create ();
    }


    private String getEncodedInfo() {
        String login = username + ":" + password;
        return DatatypeConverter.printBase64Binary ( login.getBytes () );
    }


    public String[] retrieveQueuesFromRabbitMQByTemplate () {

        String encoded = getEncodedInfo ();
        RequestCallback requestCallback = request-> request.getHeaders ().set( "Authorization", "Basic " + encoded );
        ResponseExtractor<String> extractor = new HttpMessageConverterExtractor<> ( String.class, template.getMessageConverters () );
        String result = template.execute ( MQ_URL, HttpMethod.GET,requestCallback,extractor );
        JSONArray objectArray = new JSONArray (result);
        String[] queueNames = new String[objectArray.length ()];
        for (int i = 0; i < objectArray.length (); i++) {
            queueNames[i] = (String) objectArray.getJSONObject ( i ).get ( "name" );
        }
        return queueNames;
    }

    public String[] retrieveQueueNamesAsync() {

        String encoded = getEncodedInfo ();
        String stringMono = webClient.get().uri ( MQ_URL ).header ("Authorization", "Basic " + encoded).retrieve ().bodyToMono (String.class).block ();
        JSONArray objectArray = new JSONArray (stringMono);
        String[] queueNames = new String[objectArray.length ()];
        for (int i = 0; i < objectArray.length (); i++) {
            queueNames[i] = (String) objectArray.getJSONObject ( i ).get ( "name" );
        }
        return queueNames;
    }



}

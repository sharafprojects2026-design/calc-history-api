package com.age.calc.soap;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.ws.Endpoint;

@Component
public class SoapPublisher {

    // 0.0.0.0 = accepte les connexions du réseau (téléphone)
    private static final String URL = "http://0.0.0.0:8081/soap/calculatrice";
    private Endpoint endpoint;

    @PostConstruct
    public void publier() {
        endpoint = Endpoint.publish(URL, new CalculatriceServiceImpl());
        System.out.println("SOAP publié : " + URL + "?wsdl");
    }

    @PreDestroy
    public void arreter() {
        if (endpoint != null) {
            endpoint.stop();
        }
    }
}
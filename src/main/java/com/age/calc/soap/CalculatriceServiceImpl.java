package com.age.calc.soap;

import com.age.calc.soap.generated.CalculException;
import com.age.calc.soap.generated.CalculException_Exception;
import com.age.calc.soap.generated.CalculatriceService;

import javax.jws.WebService;

@WebService(
        serviceName = "CalculatriceService",
        portName = "CalculatricePort",
        targetNamespace = "http://calc.age.com/soap",
        endpointInterface = "com.age.calc.soap.generated.CalculatriceService"
)
public class CalculatriceServiceImpl implements CalculatriceService {

    @Override
    public double addition(double a, double b) {
        return a + b;
    }

    @Override
    public double division(double a, double b) throws CalculException_Exception {
        if (b == 0) {
            throw erreur("Division par zéro impossible");
        }
        return a / b;
    }

    @Override
    public double puissance(double base, double exposant) {
        return Math.pow(base, exposant);
    }

    @Override
    public double racine(double x) throws CalculException_Exception {
        if (x < 0) {
            throw erreur("Racine d'un nombre négatif impossible");
        }
        return Math.sqrt(x);
    }

    @Override
    public double factorielle(int n) throws CalculException_Exception {
        if (n < 0) {
            throw erreur("Factorielle d'un nombre négatif impossible");
        }
        if (n > 170) {
            throw erreur("Factorielle trop grande (maximum 170)");
        }
        double r = 1;
        for (int i = 2; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    private CalculException_Exception erreur(String message) {
        CalculException fault = new CalculException();
        fault.setMessage(message);
        return new CalculException_Exception(message, fault);
    }
}
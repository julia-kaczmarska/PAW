package com.jsfcourse.calc;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.text.NumberFormat;
import java.util.Locale;

@Named
@RequestScoped
public class KredBB {
    private String kwota;
    private String lata;
    private String procent;
    private Double result;
    public boolean nope = true;

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getLata() {
        return lata;
    }

    public void setLata(String lata) {
        this.lata = lata;
    }

    public String getProcent() {
        return procent;
    }

    public void setProcent(String procent) {
        this.procent = procent;
    }

    public Double getResult() {
        return result;
    }

    public String calculateLoan() {
        // Validation
        if (kwota == null || lata == null || procent == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzupełnij wszystkie pola",null));
            return null; // Exit the method on validation failure
        }

        try {
            Double loanAmount = Double.parseDouble(kwota);
            int loanYears = Integer.parseInt(lata);
            Double interestRate = Double.parseDouble(procent);

            // Additional validation
            if (loanAmount <= 0 || loanYears <= 0 || interestRate <= 0) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proszę wprowadzić dodatnie liczby",null));
                return null; // Exit the method on validation failure
            }

            // Perform the loan calculation (e.g., simple interest formula)
            
            Double monthlyInterestRate = interestRate / 100 / 12;
            Double numberOfPayments = (double)loanYears * 12;
            result = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        } catch (NumberFormatException e) {
            // Handle number format exceptions
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawne wartości",null));
            nope = false;
        }
        
        if(nope) {
        	return "showresult"; // This is the navigation outcome
        } else {
            return null; // If validation fails, stay on the same page
        }
        	
    }
    
    public String goBackToCalculator() {
    	return "index"; // This is the navigation outcome

    }
}

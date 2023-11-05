package com.jsfcourse.calc;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;


import java.text.NumberFormat;
import java.util.Locale;

@Named
@RequestScoped
public class KredBB {
    private String kwota;
    private String lata;
    private String procent;
    private Double result;

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
    
    public void setResult(Double result) {
        this.result = result;
    }

    public boolean calculateLoan() {
    	try {
    		if (this.kwota == null || this.lata == null || this.procent == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzupełnij wszystkie pola",null));
                return false;
            }
    		
            Double loanAmount = Double.parseDouble(kwota);
            int loanYears = Integer.parseInt(lata);
            Double interestRate = Double.parseDouble(procent);
            
            if (loanAmount <= 0 || loanYears <= 0 || interestRate <= 0) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proszę wprowadzić dodatnie liczby",null));
                return false;
            }
            
            Double monthlyInterestRate = interestRate / 100 / 12;
            Double numberOfPayments = (double)loanYears * 12;
            result = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
            return true;

        } catch (NumberFormatException e) {
        	FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawne wartości",null));
            return false;
        }
    }


    public String calc() {
    	if(calculateLoan()) {
        	return "showresult";
        }
        return null;
    }
    
    public String goBackToCalculator() {
    	return "index";
    }
}
    
        	

 

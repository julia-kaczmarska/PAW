package com.jsfcourse.calc;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.text.NumberFormat;
import java.util.Locale;

@Named
@RequestScoped
public class KredBB {
    private Double kwota;
    private int lata;
    private Double procent;
    private Double result;

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    public int getLata() {
        return lata;
    }

    public void setLata(int lata) {
        this.lata = lata;
    }

    public Double getProcent() {
        return procent;
    }

    public void setProcent(Double procent) {
        this.procent = procent;
    }

    public Double getResult() {
        return result;
    }
    
    public void setResult(Double result) {
        this.result = result;
    }
    
	@Inject
	FacesContext ctx;

    public boolean calculateLoan() {
    	try {
    		if (this.kwota == null || this.lata == 0 || this.procent == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzupełnij wszystkie pola",null));
                return false;
            }
            
            if (kwota <= 0 || lata <= 0 || procent <= 0) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proszę wprowadzić dodatnie liczby",null));
                return false;
            }
            
            Double monthlyInterestRate = kwota / 100 / 12;
            Double numberOfPayments = (double)lata * 12;
            result = (procent * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
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
    
        	

 

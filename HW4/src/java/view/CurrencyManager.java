/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CurrConvertBean;
import java.io.Serializable;
import javax.ejb.EJB;
//import javax.enterprise.context.Conversation;

import javax.enterprise.context.SessionScoped;

import javax.inject.Named;


/**
 *
 * @author danpan
 */
@Named("currencyManager")
@SessionScoped
public class CurrencyManager implements Serializable {
    private static final long serialVersionUID = 1427569293714564614L;
    
    private Float amount;
    private Exception convertFailure;
    @EJB
    private CurrConvertBean currConvertBean;
    private String currOrigin = null;
    private String currTarget= null;
    private float result;
   
    
   
    
    private void handleException(Exception e) {
        
        e.printStackTrace(System.err);
        convertFailure = e;
    }
    
   public void setCurrOrigin(String currOrigin){
        this.currOrigin=currOrigin;
    }
    
    public String getCurrOrigin(){
        return this.currOrigin;
    }
    public void setCurrTarget(String currTarget){
        this.currTarget=currTarget;
    }
    
    public String getCurrTarget(){
        return this.currTarget;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    public Float getAmount() {
        return this.amount;
    }
    
   
    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }
    
    public String convert() {
        try {
           
            convertFailure = null;
           result=this.currConvertBean.convert(this.currOrigin, this.currTarget, amount);
            
            
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
    
    public float getResult(){
        return result;
    }
public Exception getException() {
        return convertFailure;
    }
public boolean getSuccess() {
        return convertFailure == null;
    }
   
}

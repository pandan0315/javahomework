/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Currency;


/**
 *
 * @author danpan
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CurrConvertBean {
    @PersistenceContext(unitName = "currencyPU")
    private EntityManager em;

    /**
     *
     * @param originalCurrency
     * @param targetCurrency
     * @param amount
     * @return
     */
    public float convert(String originalCurrency, String targetCurrency,  float amount) {
        Currency original=em.find(Currency.class, originalCurrency);
        Currency target =em.find(Currency.class, targetCurrency);
        
        return (original.getCurrencyRate()/target.getCurrencyRate())*amount;
        
        
        
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

   
}
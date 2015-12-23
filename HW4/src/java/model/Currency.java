/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQuery;

/**
 *
 * @author danpan
 * 
 * 
 */

@Entity(name="Currency")
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id    
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String currencyName;
    
    private float currencyRate;
    
   /* private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/
    public Currency(){
        
    }
    public Currency(String currencyName,float currencyRate){
        this.currencyName=currencyName;
        this.currencyRate=currencyRate;
        
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyName != null ? currencyName.hashCode() : 0);
        return hash;
    }

   @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        return !((this.currencyName == null && other.currencyName != null) || (this.currencyName != null && !this.currencyName.equals(other.currencyName)));
    }

    @Override
    public String toString() {
        return "model.Currency[ id=" + currencyName + " ]";
    }

    public void setCurrencyRate(float currencyRate){
        this.currencyRate=currencyRate;
    }

   public String getCurrencyName(){
       return this.currencyName;
   }
    public float getCurrencyRate() {
        return this.currencyRate;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
    
}

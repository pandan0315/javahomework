/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencydbmanager;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danpan
 */
@Entity
@Table(name = "CURRENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
    @NamedQuery(name = "Currency.findByCurrencyname", query = "SELECT c FROM Currency c WHERE c.currencyname = :currencyname"),
    @NamedQuery(name = "Currency.findByCurrencyrate", query = "SELECT c FROM Currency c WHERE c.currencyrate = :currencyrate")})
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@Basic(optional = false)
    @Column(name = "CURRENCYNAME")
    private String currencyname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CURRENCYRATE")
    private Float currencyrate;

    public Currency() {
    }
    
    public Currency(String currencyName,float currencyRate){
        this.currencyname=currencyName;
        this.currencyrate=currencyRate;
        
    }

    public Currency(String currencyname) {
        this.currencyname = currencyname;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public Float getCurrencyrate() {
        return currencyrate;
    }

    public void setCurrencyrate(Float currencyrate) {
        this.currencyrate = currencyrate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyname != null ? currencyname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.currencyname == null && other.currencyname != null) || (this.currencyname != null && !this.currencyname.equals(other.currencyname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "currencydbmanager.Currency[ currencyname=" + currencyname + " ]";
    }
    
}

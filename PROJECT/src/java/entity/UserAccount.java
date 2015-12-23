/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 *
 * @author danpan
 */
 @Entity
 @NamedQueries({
 @NamedQuery(name="UserAccount.control",query="SELECT u FROM UserAccount u WHERE u.username=:username and u.password=:password"),
 @NamedQuery(name="findByUsename",query="SELECT u FROM UserAccount u WHERE u.username=:username")})
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "balance")
    private float accountBalance;
    
     @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
     private List<ShoppedProduct> items;

   
    public int totalNum(){
       int num=0;
        for(ShoppedProduct item :items){
            num+=item.getShoppedNum();
            
        }
        return num;
    }

    public float totalPay(){
        float pay=0;
        for(ShoppedProduct item:items){
            pay+=item.totalPrice();
        }
        return pay;
    }
 
    public List<ShoppedProduct> getItems() {
        return items;
    }

   
    public void setItems(List<ShoppedProduct> items) {
        this.items = items;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPassword() {
        return password;
    }

    public float getAccountBalance() {
        return accountBalance;
    }
    
    public UserAccount() {
       
    }

    public String getUsername() {
        return username;
    }

 
      public UserAccount(String username, String password,float accountBalance) {
        this.items=new ArrayList<>();
       
        this.username = username;
        this.password = password;
        this.accountBalance=accountBalance;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( username!= null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserAccount[ username=" + username + " ]";
    }
 
    
    public void withdraw(float value){
        this.accountBalance-=value;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Product;
import entity.ShoppedProduct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import entity.UserAccount;
import java.util.List;


/**
 *
 * @author danpan
 */
@Stateless
public class UserFacadeBean {
    
@PersistenceContext(unitName = "projectPU")
    private EntityManager em;


    public UserAccount register(String name, String password,Integer balance) {
        UserAccount newUser=new UserAccount(name,password,balance);
        em.persist(newUser);
        return newUser;
        
    }
    
    
    public boolean login(String name, String password){
       try{
        UserAccount u=em.createNamedQuery("UserAccount.control",UserAccount.class).setParameter("username", name).setParameter("password", password).getSingleResult();
        if(u != null){
            return true;
            
        }else{
            return false;
        }
              
        
       }catch(Exception e){
           return false;
       }
       
        
        
    }

    public boolean payout(String name, float pay) {
        UserAccount user = em.createNamedQuery("findByUsename", UserAccount.class).setParameter("username", name).getSingleResult();
        float balance = user.getAccountBalance();
        if (pay > balance) {
            
        }
        else {
          
            List<ShoppedProduct> products = em.createNamedQuery("findByshoppingUser", ShoppedProduct.class).setParameter("user", user).getResultList();
            List<Product> productList=em.createQuery("SELECT p FROM Product p").getResultList();
            
            for(Product p:productList){
                for(ShoppedProduct p1:products){
                    if(p.getProductId().equals(p1.getProduct().getProductId())){
                        try{
                        p.decreaseTotalNum(p1.getShoppedNum());
                        }catch(Exception e){
                            return false;
                        }
                                
                    }
                }
            }
            for (ShoppedProduct p : products) {
                em.remove(p);
            }
            user.getItems().clear();
              user.withdraw(pay);
            return true;
        }
         return false;
    }

    public void clearCart(String name) {
       
        UserAccount user = em.createNamedQuery("findByUsename", UserAccount.class).setParameter("username", name).getSingleResult();
        List<ShoppedProduct> products = em.createNamedQuery("findByshoppingUser", ShoppedProduct.class).setParameter("user", user).getResultList();
         for (ShoppedProduct p : products) {
                em.remove(p);
          
            user.getItems().clear();
           
   
    }
}
}
    
  


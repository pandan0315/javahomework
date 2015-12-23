/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entity.Product;
import entity.ShoppedProduct;
import entity.UserAccount;
import java.util.HashMap;
//import entity.UserCart;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author danpan
 */
@Stateless
public class ProductFacadeBean {
    @PersistenceContext(unitName = "projectPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public  List<Product> getItems(){
        return em.createQuery("SELECT p FROM Product p").getResultList();
    }
    
    
    public Product returnProduct(String query){
        Product product=(Product)em.createNamedQuery("Product.query", Product.class).setParameter("productName", query).getSingleResult();
        
        return product;
    }
    
    
    public void addToCart(Product product,String name){
        UserAccount user=em.createNamedQuery("findByUsename", UserAccount.class).setParameter("username", name).getSingleResult();
        
        try{
        
        ShoppedProduct shoppedProduct=em.createNamedQuery("findByProductID", ShoppedProduct.class).setParameter("product", product).getSingleResult();
       
        shoppedProduct.incrementNum();
        }catch(Exception e){
           ShoppedProduct newItem=new ShoppedProduct(product,user);
           em.persist(newItem);

        }

    }

    public int getItemsNum(String name) {
        int num=0;
        List<ShoppedProduct> items= this.getShoppedProducts(name);
        if(items!=null){
        for(ShoppedProduct item: items){
            num+=item.getShoppedNum();
        }
        }
        return num;
 

    }
    
    
   public List<ShoppedProduct> getShoppedProducts(String name){
        UserAccount user=em.createNamedQuery("findByUsename", UserAccount.class).setParameter("username", name).getSingleResult();
        try{
        List<ShoppedProduct> items= em.createNamedQuery("findByshoppingUser",ShoppedProduct.class).setParameter("user", user).getResultList();
        return items;
        }catch(Exception e){
            return null;
        }
     //  List<ShoppedProduct> items= user.getItems();
       
       
   }

    public float getTotalPay(String name) {
        float pay=0;
         List<ShoppedProduct> items= this.getShoppedProducts(name);
         for(ShoppedProduct item:items){
             pay+=item.totalPrice();
        
    }
       return pay;
    }
    
}

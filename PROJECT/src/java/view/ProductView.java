/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProductFacadeBean;
import entity.Product;
import entity.ShoppedProduct;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author danpan
 */
@Named("productView")
@SessionScoped

public class ProductView implements Serializable {
    private static final long serialVersionUID = -2707128676125228810L;
    
    
    @EJB
    private ProductFacadeBean productFacadeBean;
   
    public ProductView() {
    }
    
    public List<Product> getProducts(){
        return this.productFacadeBean.getItems();
    }
    
    public String getQuery(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("query");
    }

    
    public Product getProduct(){
        return this.productFacadeBean.returnProduct(getQuery());
    }
    
  
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProductFacadeBean;
import entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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
    private Product newProduct=new Product();
   // private List<Product> productList=new ArrayList<>();
    private boolean edit;
    
    public ProductView() {
    }
    
   // @PostConstruct
    //public void init(){
      //  productList=this.productFacadeBean.getItems();
    //}

    public List<Product> getProductList() {
        return this.productFacadeBean.getItems();
    }

   // public void setProductList(List<Product> productList) {
     //   this.productList = productList;
    //}

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    
    //public List<Product> getProducts(){
      //  return this.productFacadeBean.getItems();
    //}
    
    public String getQuery(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("query");
    }

    
    public Product getProduct(){
        return this.productFacadeBean.returnProduct(getQuery());
    }
    
 
	
	

    public String delete(Product p){
        this.productFacadeBean.delete(p);
       // productList.remove(p);
        return "";
    }

    public Product getNewProduct() {
        return this.newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }
   
   public String addNewProduct(){
       if(!this.productFacadeBean.addNew(newProduct)){
           RequestContext.getCurrentInstance().update("growl");
        FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Product is already exsisted"));
       }
       //productList=this.productFacadeBean.getItems();
       //this.newProduct=null;
       this.newProduct =new Product();
       return "";
   }
   
   public void editProduct(Product p){
       this.newProduct=p;
       this.edit=true;
   }
   public void saveProduct(){
       this.productFacadeBean.updateProductList(newProduct);
       edit=false;
      
       //this.newProduct=null;
      this.newProduct =new Product();
   }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProductFacadeBean;
import controller.UserFacadeBean;
import entity.Product;
import entity.ShoppedProduct;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import entity.UserAccount;
import java.io.IOException;
import java.util.List;
import javax.faces.context.ExternalContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author danpan
 */
@Named("user")
@SessionScoped
public class User implements Serializable{
    
    private static final long serialVersionUID = -5493670343039888319L;
    
    @EJB
    private UserFacadeBean userFacadeBean;
    @EJB
    private ProductFacadeBean productFacadeBean;
    private String name1;
    private String password1;
    private String name2;
    private String password2;
    private String name3;
    private String password3;
    private Integer balance;
    private boolean edit;
    private UserAccount newUser=new UserAccount();
    private String userName;
   
    
   

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    
    public UserAccount getNewUser() {
        return newUser;
    }

    public void setNewUser(UserAccount newUser) {
        this.newUser = newUser;
    }

    
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return null;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public void setPassword3(String password3) {
        this.password3 = password3;
    }

    public String getName1() {
        return name1;
    }

    public String getPassword1() {
        return password1;
    }

    public String getName2() {
        return name2;
    }

    public String getPassword2() {
        return password2;
    }

    public String getName3() {
        return name3;
    }

    public String getPassword3() {
        return password3;
    }

   
   
    public List<UserAccount> getUserList(){
        return this.userFacadeBean.getUsers();
    }
     
      private String jsf22Bugfix() {
        return "";
    }
      
      
   
      
    public String register() {
        
       
       if(this.userFacadeBean.register(name2, password2,balance)==null){
        RequestContext.getCurrentInstance().update("growl");
        FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Username has been registered!"));
          
           return jsf22Bugfix();
           
       }
        
        this.userName=name2;
        
       return "inventory.xhtml";
    }
   
           
    public String login(){
         
        String result=this.userFacadeBean.login(name1,password1);
        if(result==null){
        RequestContext.getCurrentInstance().update("growl");
        FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Username or Password invalid"));
          
           
       
        }else if(result.equals("active")){
            this.userName=name1;
            
            return "inventory.xhtml";
            
        }else{
           RequestContext.getCurrentInstance().update("growl");
        FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","You are banned!"));
           
        }
        
        
         return jsf22Bugfix();
        }
    
       
    
    
    public String adminlogin(){
        
        
        if(name3.equals("admin")&&password3.equals("123456")){
           
            return "productEdit.xhtml";
            
        }else{
       RequestContext.getCurrentInstance().update("growl");
       FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Please login with user admin! "));
        } 
        return jsf22Bugfix();
    }
    
    public String logout() throws IOException {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect("../faces/index.xhtml");
        return jsf22Bugfix();
    }
    public String addToCart(){
        String query = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("query");
        Product pro = this.productFacadeBean.returnProduct(query);

        this.productFacadeBean.addToCart(pro, userName);
        return jsf22Bugfix();
    }

    public int getItemsInCart() {

        return this.productFacadeBean.getItemsNum(userName);
    }

    public List<ShoppedProduct> getShoppedProducts() {
        return this.productFacadeBean.getShoppedProducts(userName);
    }

    public float getToPay() {
        return this.productFacadeBean.getTotalPay(userName);
    }

    public String payout() {
        float finalPay = this.getToPay();
        boolean isSuccess = this.userFacadeBean.payout(userName, finalPay);
        if (!isSuccess) {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Check your balance or product storage!"));
        }

        return jsf22Bugfix();

    }

    public String clear() {

        this.userFacadeBean.clearCart(userName);
         
        return jsf22Bugfix(); 
     }
    
    public String editUser(UserAccount usr){
        this.newUser=usr;
        this.edit=true;
        return jsf22Bugfix();  
    }
   public void saveUserStatus(){
       this.userFacadeBean.updateUserStatus(newUser);
       edit=false;
   }
}


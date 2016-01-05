/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author danpan
 */
@Entity
@NamedQueries({
@NamedQuery(name="findByProductID",query="SELECT p FROM ShoppedProduct p WHERE p.product=:product"),
@NamedQuery(name="findByshoppingUser",query="SELECT p FROM ShoppedProduct p WHERE p.user=:user"),
@NamedQuery(name="findByUserAndProduct",query="SELECT p FROM ShoppedProduct p WHERE p.user=:user and p.product=:product")
})
public class ShoppedProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "shoppedNUM")
    private int shoppedNum; 
    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName="productID")
    private Product product;   
    
    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName="username")
    private UserAccount user;

    public ShoppedProduct() {
    }

    public ShoppedProduct(Product product,UserAccount user) {
        this.product = product;
        this.shoppedNum=1;
        this.user=user;
        
    }

    public float totalPrice(){
       
       return this.shoppedNum*product.getProductPrice();
    }
   
    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    
    
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getShoppedNum() {
        return shoppedNum;
    }

    public void setShoppedNum(int shoppedNum) {
        this.shoppedNum = shoppedNum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    public void incrementNum(){
       this.shoppedNum++;
    }
    public void decrementQuantity(){
        this.shoppedNum--;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppedProduct)) {
            return false;
        }
        ShoppedProduct other = (ShoppedProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ShoppedProduct[ id=" + id + " ]";
    }
    
}

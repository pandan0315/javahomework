/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author danpan
 */
@Entity
@NamedQuery(name="Product.query",query="SELECT p FROM Product p WHERE p.productName=:productName")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productID")
    private Integer productId;
    @Column(name = "productName")
    private String productName;
    @Column(name = "productPrice")
    private float productPrice;
    @Column(name = "productNum")
    private int productNum;

    public Product() {
       
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public int getProductNum() {
        return productNum;
    }
    

    

    public Product(Integer id, String productName, float productPrice,int productNum) {
        this.productId = id;
        this.productName =productName;
        this.productPrice = productPrice;
        this.productNum=productNum;
        
    }
    
    public Product(Integer id, String productName, float productPrice) {
        this.productId = id;
        this.productName =productName;
        this.productPrice = productPrice;
       
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Product[ id=" + this.productId + " ]";
    }
    
    public void decreaseTotalNum(int shoppedTotal) throws Exception{
        if(shoppedTotal>this.productNum){
            throw new Exception();
        }
        this.productNum-=shoppedTotal;
    }
}

package entity;

import entity.Product;
import entity.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-02T12:09:39")
@StaticMetamodel(ShoppedProduct.class)
public class ShoppedProduct_ { 

    public static volatile SingularAttribute<ShoppedProduct, Product> product;
    public static volatile SingularAttribute<ShoppedProduct, Integer> id;
    public static volatile SingularAttribute<ShoppedProduct, UserAccount> user;
    public static volatile SingularAttribute<ShoppedProduct, Integer> shoppedNum;

}
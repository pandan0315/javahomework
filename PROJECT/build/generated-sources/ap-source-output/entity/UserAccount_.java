package entity;

import entity.ShoppedProduct;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-02T12:09:39")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile SingularAttribute<UserAccount, String> password;
    public static volatile SingularAttribute<UserAccount, Float> accountBalance;
    public static volatile ListAttribute<UserAccount, ShoppedProduct> items;
    public static volatile SingularAttribute<UserAccount, String> username;
    public static volatile SingularAttribute<UserAccount, String> status;

}
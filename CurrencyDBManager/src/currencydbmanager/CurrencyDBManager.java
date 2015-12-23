/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencydbmanager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author danpan
 */
public class CurrencyDBManager {
    public static void main(String[] args) {
        String currencyName = args[0];
        float currencyRate = Float.valueOf(args[1]);
        //String currencyName = "RUP";
        //float currencyRate = 11;
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "CurrencyDBManagerPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        Currency existed_c = entitymanager.find(Currency.class, currencyName);
        if (existed_c == null) {
            entitymanager.getTransaction( ).begin( );
            Currency c = new Currency(currencyName, currencyRate);
            entitymanager.persist( c );
            entitymanager.getTransaction( ).commit( );
        } else {
            entitymanager.getTransaction( ).begin( );
            existed_c.setCurrencyrate(currencyRate);
            entitymanager.getTransaction( ).commit( );
        }
        entitymanager.close( );
        emfactory.close( );
        
        
    }
    
}

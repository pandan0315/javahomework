
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Currency;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danpan
 */
public class StoreRate {
        @PersistenceContext(unitName = "currencyPU")
        private static EntityManager em;
        
    
    public static void main(String[] args){
        

                 em.getTransaction().begin();

                Currency currency1 = new Currency("SEK", (float) 0.1178);
                Currency currency2 = new Currency("EUR", (float) 1.0877);
                Currency currency3 = new Currency("CNY", (float) 0.1564);
                Currency currency4 = new Currency("USD", (float) 1);

                   em.persist(currency1);
                   em.persist(currency2);
                   em.persist(currency3);
                   em.persist(currency4);

                em.getTransaction().commit();
    
}
}
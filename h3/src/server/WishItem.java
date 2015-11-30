/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;

/**
 *
 * @author danpan
 */
public class WishItem implements Serializable{
    
    private final String name;
    private final float price;
    private final String wisher;
    
    public WishItem(String name, float price, String wisher) {
        this.name = name;
        this.price = price;
        this.wisher = wisher;
    }
    
    public String getWishItemName(){
        return this.name;
    }
    public float getWishprice(){
        return this.price;
    }
    public String getWisher(){
        return this.wisher;
    }
    
    @Override
    public String toString() {

        return name + " : " + price;
    }
}

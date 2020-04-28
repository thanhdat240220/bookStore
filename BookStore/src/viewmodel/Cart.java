/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.ArrayList;
/**
 *
 * @author T440s
 */
public class Cart {
    public ArrayList<CartDetail> details;
    public double totalPrice;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    
    public Cart(){
        this.details=new ArrayList<CartDetail>();
    }

    public void setDetails(ArrayList<CartDetail> details) {
        this.details = details;
    }

    public ArrayList<CartDetail> getDetails() {
        return details;
    }
}

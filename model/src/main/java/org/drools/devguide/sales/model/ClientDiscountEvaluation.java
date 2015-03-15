/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.sales.model;

/**
 *
 * @author esteban
 */
public class ClientDiscountEvaluation {
    private Client client;
    private float discount = 0.0f;

    public ClientDiscountEvaluation(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
    
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}

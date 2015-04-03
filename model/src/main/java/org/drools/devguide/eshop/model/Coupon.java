/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.eshop.model;

/**
 *
 * @author salaboy
 */
public class Coupon {
    public enum CouponType{DISCOUNT, TWOFORONE, POINTS };
    
    private Client client;
    private Order order;
    private CouponType type;

    public Coupon() {
    }

    public Coupon(Client client, Order order, CouponType type) {
        this.client = client;
        this.order = order;
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Coupon{" + "client=" + client + ", order=" + order + ", type=" + type + '}';
    }
    
    
    
    
    
}

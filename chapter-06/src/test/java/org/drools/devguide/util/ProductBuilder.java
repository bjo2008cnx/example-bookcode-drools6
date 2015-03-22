/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.util;

import org.drools.devguide.eshop.model.Product;

/**
 *
 * @author esteban
 */
public class ProductBuilder {
    private final OrderItemBuilder superBuilder;
    private final Product instance;

    public ProductBuilder(OrderItemBuilder superBuilder) {
        this.superBuilder = superBuilder;
        
        this.instance = new Product();
        //default values
        this.instance.setId(1L);
        this.instance.setCost(0.0);
        this.instance.setName("");
        this.instance.setSalePrice(0.0);
    }
    
    public ProductBuilder withId(long id){
        this.instance.setId(id);
        return this;
    }
    
    public ProductBuilder withCost(double cost){
        this.instance.setCost(cost);
        return this;
    }
    
    public ProductBuilder withName(String name){
        this.instance.setName(name);
        return this;
    }
    
    public ProductBuilder withSalePrice(double salePrice){
        this.instance.setSalePrice(salePrice);
        return this;
    }
    
    public Product build(){
        return this.instance;
    }
    
    public OrderItemBuilder end(){
        return superBuilder;
    } 

}

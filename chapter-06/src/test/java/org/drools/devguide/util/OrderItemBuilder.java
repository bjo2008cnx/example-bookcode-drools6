/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.util;

import java.util.Optional;
import org.drools.devguide.eshop.model.OrderItem;

/**
 *
 * @author esteban
 */
public class OrderItemBuilder {
    
    private final OrderBuilder superBuilder;
    private final OrderItem instance;
    
    private Optional<ProductBuilder> productBuilder = Optional.empty();
    
    public OrderItemBuilder(OrderBuilder superBuilder) {
        this.superBuilder = superBuilder;
        
        this.instance = new OrderItem();
        this.instance.setQuantity(0);
    }
    
    public OrderItemBuilder withQuantity(int quantity){
        this.instance.setQuantity(quantity);
        return this;
    }
    
    public ProductBuilder withProduct(){
        this.productBuilder = Optional.of(new ProductBuilder(this));
        return this.productBuilder.get();
    }
    
    public OrderItem build(){
        if (this.productBuilder.isPresent()){
            this.instance.setProduct(this.productBuilder.get().build());
        }
        return this.instance;
    }
    
    public OrderBuilder end(){
        return superBuilder;
    }
    
}

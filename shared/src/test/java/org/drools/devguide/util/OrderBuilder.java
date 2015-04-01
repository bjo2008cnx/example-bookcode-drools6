/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.drools.devguide.eshop.model.Order;
import org.drools.devguide.eshop.model.OrderState;

/**
 *
 * @author esteban
 */
public class OrderBuilder {
    
    private final ClientBuilder superBuilder;
    private final Order instance;
    private Optional<OrderItemBuilder> orderItemBuilder = Optional.empty();
    
    public OrderBuilder(ClientBuilder superBuilder) {
        this.superBuilder = superBuilder;
        
        this.instance = new Order();
        //default values for the new Order
        this.instance.setState(OrderState.PENDING);
        this.instance.setDate(new Date());
        this.instance.setItems(new ArrayList<>());
        
    }

    public OrderBuilder withSate(OrderState state){
        this.instance.setState(state);
        return this;
    }
    
    public OrderBuilder withDate(Date date){
        this.instance.setDate(date);
        return this;
    }
    
    public OrderItemBuilder newItem(){
        if (this.orderItemBuilder.isPresent()){
            this.instance.getItems().add(this.orderItemBuilder.get().build());
        }
        this.orderItemBuilder = Optional.of(new OrderItemBuilder(this));
        return this.orderItemBuilder.get();
    }
    
    public Order build(){
        if (this.orderItemBuilder.isPresent()){
            this.instance.getItems().add(this.orderItemBuilder.get().build());
        }
        return this.instance;
    }
    
    public ClientBuilder end(){
        return superBuilder;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.util;

import java.util.Optional;
import java.util.UUID;
import org.drools.devguide.eshop.model.Client;
import org.drools.devguide.eshop.model.Order;

/**
 *
 * @author esteban
 */
public class ClientBuilder {
    
    private final Client instance;
    private Optional<OrderBuilder> orderBuilder = Optional.empty();

    public ClientBuilder() {
        instance = new Client();
        instance.setId(UUID.randomUUID().toString());
    }
    
    public ClientBuilder withId(String id){
        instance.setId(id);
        return this;
    }
    
    public OrderBuilder newOrder(){
        if (this.orderBuilder.isPresent()){
            Order order = this.orderBuilder.get().build();
            order.setClient(this.instance);
            this.instance.getOrders().add(order);
        }
        this.orderBuilder = Optional.of(new OrderBuilder(this));
        return this.orderBuilder.get();
    }
    
    public Client build(){
        if (this.orderBuilder.isPresent()){
            Order order = this.orderBuilder.get().build();
            order.setClient(this.instance);
            this.instance.getOrders().add(order);
        }
        return instance;
    }

    public ClientBuilder end() {
        return this;
    }
    
}

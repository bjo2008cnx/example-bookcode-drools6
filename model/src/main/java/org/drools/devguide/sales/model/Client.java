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
public class Client {

    public static enum ClientCategory {
        REGULAR, FREQUENT, ELITE, PREMIUM;
    }

    private String id;
    private ClientCategory category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientCategory getCategory() {
        return category;
    }

    public void setCategory(ClientCategory category) {
        this.category = category;
    }
}

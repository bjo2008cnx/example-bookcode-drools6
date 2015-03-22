/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.eshop.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author esteban
 */
public class SuspiciousOperation {
    
    public static enum Type {
        SUSPICIOUS_AMOUNT,
        SUSPICIOUS_FREQUENCY;
    }
    
    private Client client;
    private Type type;
    private Date date;
    private String comment;

    public SuspiciousOperation() {
    }

    public SuspiciousOperation(Client client, Type type) {
        this.client = client;
        this.type = type;
    }

    public SuspiciousOperation(Client client, Type type, Date date) {
        this.client = client;
        this.type = type;
        this.date = date;
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.client);
        hash = 47 * hash + Objects.hashCode(this.type);
        hash = 47 * hash + Objects.hashCode(this.date);
        hash = 47 * hash + Objects.hashCode(this.comment);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuspiciousOperation other = (SuspiciousOperation) obj;
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SuspiciousOperation [" + "client=" + client + ", type=" + type + ", date=" + date + ", comment=" + comment + ']';
    }
    
}

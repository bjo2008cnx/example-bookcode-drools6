/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.sales.service;

import java.util.Set;
import org.drools.devguide.sales.model.Client;

/**
 *
 * @author esteban
 */
public interface ClientService {
    public Set<Client> getAllClients();
    
}

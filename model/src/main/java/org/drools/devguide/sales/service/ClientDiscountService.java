/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.sales.service;

import org.drools.devguide.sales.model.ClientDiscountEvaluation;

/**
 *
 * @author esteban
 */
public interface ClientDiscountService {
    public void persist(ClientDiscountEvaluation cde);
}

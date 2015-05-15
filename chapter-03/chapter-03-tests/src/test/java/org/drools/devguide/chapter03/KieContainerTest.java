package org.drools.devguide.chapter03;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.devguide.BaseTest;
import org.drools.devguide.eshop.model.Customer;
import org.drools.devguide.eshop.model.Item;
import org.drools.devguide.eshop.model.Item.Category;
import org.drools.devguide.eshop.model.Order;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author salaboy
 */
public class KieContainerTest {

    @Test
    public void loadingRulesFromExistingArtifact() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("org.drools.devguide", "chapter-03-kjar-simple-discounts", "0.1-SNAPSHOT"));
        for (String s : kContainer.getKieBaseNames()) {
            System.out.println("KieBase: "+s);
        }

        KieSession kieSession = kContainer.newKieSession("rules.discount");

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.SILVER);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();
        System.out.println("Fired: "+fired);

        assertThat(1, is(fired));
        assertThat(10, is(order.getDiscount()));




    }
    

}

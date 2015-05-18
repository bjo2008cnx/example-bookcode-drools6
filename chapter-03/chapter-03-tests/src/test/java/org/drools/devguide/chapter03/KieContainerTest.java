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
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
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
    public void loadingRulesFromClassPath() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieClasspathContainer();

        Results results = kContainer.verify();
        for (Message message : results.getMessages()) {
            System.out.println(">> message: "+message);

        }
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        KieSession kieSession = kContainer.newKieSession("rules.cp.discount.session");

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.BRONZE);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();

        assertThat(1, is(fired));
        assertThat(5.0, is(order.getDiscount().getPercentage()));


    }

    @Test
    public void loadingRulesFromExistingArtifact() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("org.drools.devguide", "chapter-03-kjar-simple-discounts", "0.1-SNAPSHOT"));

        Results results = kContainer.verify();
        for (Message message : results.getMessages()) {
            System.out.println(">> message: "+message);

        }
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        KieSession kieSession = kContainer.newKieSession("rules.simple.discount");

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.SILVER);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();

        assertThat(1, is(fired));
        assertThat(10.0, is(order.getDiscount().getPercentage()));

    }


    @Test
    public void loadingRulesFromAnotherExistingArtifact() {
        KieServices ks = KieServices.Factory.get();

        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("org.drools.devguide", "chapter-03-kjar-premium-discounts", "0.1-SNAPSHOT"));

        Results results = kContainer.verify();
        for (Message message : results.getMessages()) {
            System.out.println(">> message: "+message);

        }
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        KieSession kieSession = kContainer.newKieSession("rules.premium.discount");

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.GOLD);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();

        assertThat(1, is(fired));
        assertThat(20.0, is(order.getDiscount().getPercentage()));




    }


    @Test
    public void loadingRulesFromParentExistingArtifact() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("org.drools.devguide", "chapter-03-kjar-parent", "0.1-SNAPSHOT"));

        Results results = kContainer.verify();
        for (Message message : results.getMessages()) {
            System.out.println(">> message: "+message);

        }
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        KieSession kieSession = kContainer.newKieSession("rules.discount");

        Customer customerGold = new Customer();
        customerGold.setCategory(Customer.Category.GOLD);

        Order orderGold = new Order();
        orderGold.setCustomer(customerGold);

        kieSession.insert(customerGold);
        kieSession.insert(orderGold);

        int fired = kieSession.fireAllRules();
        assertThat(1, is(fired));
        assertThat(20.0, is(orderGold.getDiscount().getPercentage()));

        Customer customerSilver = new Customer();
        customerSilver.setCategory(Customer.Category.SILVER);

        Order orderSilver = new Order();
        orderSilver.setCustomer(customerSilver);

        kieSession.insert(customerSilver);
        kieSession.insert(orderSilver);

        fired = kieSession.fireAllRules();
        /// BUGZILLA HERE??? if I just want to fire all the rules after inserting all the facts
        // the Silver Rule is executed twice (no idea why!!!!)

        assertThat(1, is(fired));
        assertThat(10.0, is(orderSilver.getDiscount().getPercentage()));



    }

}

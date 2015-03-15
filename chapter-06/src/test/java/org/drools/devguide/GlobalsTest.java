/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide;

import org.drools.devguide.sales.model.ClientDiscountEvaluation;
import org.drools.devguide.sales.service.ClientDiscountService;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.drools.devguide.sales.model.Client;
import org.drools.devguide.sales.service.ClientService;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author esteban
 */
public class GlobalsTest {

    @Test
    public void applyDiscountToFixedCategoriesTest() throws FileNotFoundException {

        Client frequentClient = new Client();
        frequentClient.setCategory(Client.ClientCategory.FREQUENT);
        ClientDiscountEvaluation frequentClientDiscount = new ClientDiscountEvaluation(frequentClient);

        Client eliteClient = new Client();
        eliteClient.setCategory(Client.ClientCategory.ELITE);
        ClientDiscountEvaluation eliteClientDiscount = new ClientDiscountEvaluation(eliteClient);

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-fixed");
        ksession.insert(frequentClientDiscount);
        ksession.insert(eliteClientDiscount);

        ksession.fireAllRules();
        ksession.dispose();

        assertThat(frequentClientDiscount.getDiscount(), is(0.0f));
        assertThat(eliteClientDiscount.getDiscount(), is(0.1f));

    }

    @Test
    public void applyDiscountToVariableCategoriesTest() throws FileNotFoundException {

        Client frequentClient = new Client();
        frequentClient.setCategory(Client.ClientCategory.FREQUENT);
        ClientDiscountEvaluation frequentClientDiscount = new ClientDiscountEvaluation(frequentClient);

        Client eliteClient = new Client();
        eliteClient.setCategory(Client.ClientCategory.ELITE);
        ClientDiscountEvaluation eliteClientDiscount = new ClientDiscountEvaluation(eliteClient);

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-variable");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});

        ksession.insert(frequentClientDiscount);
        ksession.insert(eliteClientDiscount);

        ksession.fireAllRules();
        ksession.dispose();

        assertThat(frequentClientDiscount.getDiscount(), is(0.0f));
        assertThat(eliteClientDiscount.getDiscount(), is(0.1f));

    }

    @Test
    public void calculateDiscountsToAllClientsTest() throws FileNotFoundException {

        ClientService clientServiceImpl = new ClientService() {

            Set<Client> clients = new HashSet<>();

            {
                Client frequentClient = new Client();
                frequentClient.setCategory(Client.ClientCategory.FREQUENT);
                clients.add(frequentClient);

                Client eliteClient = new Client();
                eliteClient.setCategory(Client.ClientCategory.ELITE);
                clients.add(eliteClient);
            }

            @Override
            public Set<Client> getAllClients() {
                return clients;
            }
        };
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-service");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);

        ksession.insert("calculate discounts");

        ksession.fireAllRules();

        //Retrieve the facts of type ClientDiscountEvaluation from the session.
        Collection<FactHandle> discontFacts = ksession.getFactHandles(new ObjectFilter() {
            
            @Override
            public boolean accept(Object object) {
                return object instanceof ClientDiscountEvaluation;
            }
        });
        
        assertThat(discontFacts.size(), is(2));

    }
    
    @Test
    public void calculateDiscountsToAllClientsAndGetResultsTest() throws FileNotFoundException {
        
        List<ClientDiscountEvaluation> results = new ArrayList<>();

        ClientService clientServiceImpl = new ClientService() {

            Set<Client> clients = new HashSet<>();

            {
                Client frequentClient = new Client();
                frequentClient.setCategory(Client.ClientCategory.FREQUENT);
                clients.add(frequentClient);

                Client eliteClient = new Client();
                eliteClient.setCategory(Client.ClientCategory.ELITE);
                clients.add(eliteClient);
            }

            @Override
            public Set<Client> getAllClients() {
                return clients;
            }
        };
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-global-list");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);
        ksession.setGlobal("results", results);

        ksession.insert("calculate discounts");

        ksession.fireAllRules();

        assertThat(results.size(), is(2));

    }
    
    @Test
    public void calculateDiscountsToAllClientsAndPersistResultsTest() throws FileNotFoundException {
        
        List<ClientDiscountEvaluation> results = new ArrayList<>();

        ClientService clientServiceImpl = new ClientService() {

            Set<Client> clients = new HashSet<>();

            {
                Client frequentClient = new Client();
                frequentClient.setCategory(Client.ClientCategory.FREQUENT);
                clients.add(frequentClient);

                Client eliteClient = new Client();
                eliteClient.setCategory(Client.ClientCategory.ELITE);
                clients.add(eliteClient);
            }

            @Override
            public Set<Client> getAllClients() {
                return clients;
            }
        };
        
        
        final AtomicInteger counter = new AtomicInteger(0);
        ClientDiscountService clientDiscountServiceImpl = new ClientDiscountService() {

            @Override
            public void persist(ClientDiscountEvaluation cde) {
                //keep track of each time this method gets invoked.
                counter.incrementAndGet();
            }
        };
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-global-persist");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);
        ksession.setGlobal("results", results);
        ksession.setGlobal("clientDiscountService", clientDiscountServiceImpl);

        ksession.insert("calculate discounts");

        ksession.fireAllRules();

        assertThat(counter.get(), is(2));

    }

}

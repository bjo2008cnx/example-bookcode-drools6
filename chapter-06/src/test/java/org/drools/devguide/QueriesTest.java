/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide;

import org.drools.devguide.sales.model.ClientDiscountEvaluation;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.drools.devguide.sales.model.Client;
import org.drools.devguide.sales.service.ClientService;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;

/**
 *
 * @author esteban
 */
public class QueriesTest {

    @Test
    public void getAllDiscountsWithOnDemandQuery() throws FileNotFoundException {

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

        Channel discountStorage = new Channel() {
            @Override
            public void send(Object object) {
            }
        };

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-query");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);
        ksession.setGlobal("results", results);

        ksession.registerChannel("discount-storage", discountStorage);

        ksession.insert("calculate discounts");

        ksession.fireAllRules();

        QueryResults queryResults = ksession.getQueryResults("Get All Client Discounts");
        for (QueryResultsRow queryResult : queryResults) {
            ClientDiscountEvaluation cde = (ClientDiscountEvaluation) queryResult.get("$cde");
            //do whatever we want with cde
            //...
        }

        assertThat(queryResults.size(), is(2));

    }

    @Test
    public void getAllDiscountsWithLiveQuery() throws FileNotFoundException {
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

        Channel discountStorage = new Channel() {
            @Override
            public void send(Object object) {
            }
        };

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-query");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);
        ksession.setGlobal("results", results);

        ksession.registerChannel("discount-storage", discountStorage);

        ksession.insert("calculate discounts");


        LiveQuery liveQuery = ksession.openLiveQuery("Get All Client Discounts", null, new ViewChangedEventListener() {

            @Override
            public void rowInserted(Row row) {
                System.out.println("");
            }

            @Override
            public void rowDeleted(Row row) {
            }

            @Override
            public void rowUpdated(Row row) {
            }
        });
        
        ksession.fireAllRules();
        
        liveQuery.close();
        
    }

}

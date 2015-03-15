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
import java.util.concurrent.atomic.AtomicInteger;
import org.drools.devguide.sales.model.Client;
import org.drools.devguide.sales.service.ClientService;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author esteban
 */
public class ChannelsTest {

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
        Channel discountStorage = new Channel() {

            @Override
            public void send(Object object) {
                //keep track of each time this method gets invoked.
                counter.incrementAndGet();
            }
        };
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSession ksession = kContainer.newKieSession("discount-channel-persist");

        ksession.setGlobal("discountCategories", new Client.ClientCategory[]{Client.ClientCategory.ELITE, Client.ClientCategory.PREMIUM});
        ksession.setGlobal("clientService", clientServiceImpl);
        ksession.setGlobal("results", results);
        
        ksession.registerChannel("discount-storage", discountStorage);

        ksession.insert("calculate discounts");

        ksession.fireAllRules();

        assertThat(counter.get(), is(2));

    }

}

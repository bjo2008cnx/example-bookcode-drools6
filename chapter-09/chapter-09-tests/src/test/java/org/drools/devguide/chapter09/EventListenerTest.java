/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.chapter09;

import java.util.stream.IntStream;
import org.drools.devguide.BaseTest;
import org.drools.devguide.eshop.model.Customer;
import org.drools.devguide.eshop.model.Customer.Category;
import org.drools.devguide.eshop.model.SuspiciousOperation;
import org.drools.devguide.util.CustomerBuilder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author esteban
 */
public class EventListenerTest extends BaseTest {

    @Test
    public void matchCancelledTest() {
        KieSession ksession = this.createSession("matchCancelledKsession");
//        ksession.addEventListener(new DebugAgendaEventListener());
//        ksession.addEventListener(new DebugRuleRuntimeEventListener());
        ksession.addEventListener(new AgendaEventListener(){

            @Override
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println("==>[ActivationCreatedEvent: rule: ["+event.getMatch().getRule().getName()+"]]");
            }

            @Override
            public void matchCancelled(MatchCancelledEvent event) {
            }

            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("==>[AfterActivationFiredEvent: rule: ["+event.getMatch().getRule().getName()+"]]");
            }

            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
            }

            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
            }

            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }

            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
            }
            
        });
        ksession.addEventListener(new RuleRuntimeEventListener(){

            @Override
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("==>[ObjectInsertedEventImpl: [object: ["+event.getObject()+"]]");
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("==>[ObjectUpdatedEventImpl: [object: ["+event.getObject()+"]]");
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent event) {
            }
            
        });

        Customer customer = new CustomerBuilder()
                .withId(1L)
                .withAge(24)
                .withCategory(Customer.Category.GOLD).build();

        
        ksession.insert(customer);
        
        //Generate 5 SuspiciousOperations for the customer and insert them
        //into the session.
        IntStream.range(0, 5)
                .mapToObj(i -> new SuspiciousOperation(customer, SuspiciousOperation.Type.SUSPICIOUS_AMOUNT))
                .forEach(so -> ksession.insert(so));

        ksession.fireAllRules();

        try{
            //The final result if we are testing the "Low category of GOLD customers with suspicious operations"
            //should be SILVER, but there is a conflict between 2 rules.
            assertThat(customer.getCategory(), is(Category.SILVER));
            fail("Exception expected");
        } catch (AssertionError ae){
            //Expected.
        }
    }

}

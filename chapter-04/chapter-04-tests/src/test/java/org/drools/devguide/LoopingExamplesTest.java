package org.drools.devguide;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.drools.devguide.eshop.model.Item;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

public class LoopingExamplesTest extends BaseTest {

    protected final String ksessionName = "loopingExamplesKsession";
    
    @Test
    public void testNoLoop() {
        KieSession ksession1 = createSession(ksessionName);
        Item item1 = new Item("notepad", 10.00, 20.00);
        ksession1.insert(item1);
        //to break down the rules in groups, we use agenda groups
        ksession1.getAgenda().getAgendaGroup("withoutNoLoop").setFocus();
        //because it is going to go on an infinite loop, we limit the amount of rules 
        //it can run before forcefully stopping after an amount of rule executions
        int firedRules = ksession1.fireAllRules(100);
        assertThat(firedRules, equalTo(100));
        //after running the same rule so much, sale price gets reduced every time...
        assertThat(item1.getSalePrice(), lessThan(1.00));
        
        KieSession ksession2 = createSession(ksessionName);
        Item item2 = new Item("notepad", 10.00, 20.00);
        ksession2.insert(item2);
        //we activate the group where no loop is working 
        ksession2.getAgenda().getAgendaGroup("withNoLoop").setFocus();
        //now the rule should fire only once
        firedRules = ksession2.fireAllRules();
        assertThat(1, equalTo(firedRules));
        assertThat(item2.getSalePrice(), equalTo(18.00));
    }
    
    @Test
    public void testLockOnActive() {
    	//TODO implement
    }
}

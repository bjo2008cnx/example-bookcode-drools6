package org.drools.devguide;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.drools.devguide.eshop.model.*;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Bootstrapping the Rule Engine ..." );
        
        // 1) Bootstrap a Rule Engine Session
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession();
        
        // 2) Provide information to the Rule Engine Context
        Item item = new Item("A", 123.0, 234.0);
        System.out.println( "Item Category: " + item.getCategory());
        kSession.insert(item);
        
        // 3) Execute the rules that are matching
        int fired = kSession.fireAllRules();
        System.out.println( "Number of rules executed: " + fired );
        System.out.println( "Item Category: " + item.getCategory());
    }
}

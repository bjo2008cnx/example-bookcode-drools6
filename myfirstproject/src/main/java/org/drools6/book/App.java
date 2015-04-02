package org.drools6.book;

import org.drools.devguide.eshop.model.Product;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Bootstrapping the Rule Engine ..." );
        // Bootstrapping a Rule Engine Session
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession =  kContainer.newKieSession();
        
        Product p = new Product("A", 123.0,234.0);
        System.out.println( "Product Category: " + p.getCategory()); 
        kSession.insert(p);
        int fired = kSession.fireAllRules();
        System.out.println( "Number of Rules executed = " + fired );
        System.out.println( "Product Category: " + p.getCategory()); 

    }
}

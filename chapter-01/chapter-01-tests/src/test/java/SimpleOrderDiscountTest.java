/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.drools.devguide.BaseTest;
import org.drools.devguide.eshop.model.Client;
import org.drools.devguide.eshop.model.Order;
import org.drools.devguide.eshop.model.Product;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class SimpleOrderDiscountTest extends BaseTest{

    public SimpleOrderDiscountTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void simpleApplyDiscount() {
        KieSession kSession = createDefaultSession();
        Order o = new Order();
        Client c = new Client();
        o.setItems(null);
        kSession.insert(o);
        int fired = kSession.fireAllRules();
        assertEquals(1, fired);
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.drools.devguide.BaseTest;
import org.drools.devguide.eshop.model.Product;
import org.drools.devguide.eshop.model.Product.Category;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class ClassifyProductsTest extends BaseTest{

    public ClassifyProductsTest() {
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
    public void simpleClassification() {
        KieSession kSession = createDefaultSession();
        Product p = new Product("A", 123.0,234.0);
        kSession.insert(p);
        int fired = kSession.fireAllRules();
        assertThat(1, is(fired));
        assertThat(Category.LOW_RANGE, is(p.getCategory()));
    }
}

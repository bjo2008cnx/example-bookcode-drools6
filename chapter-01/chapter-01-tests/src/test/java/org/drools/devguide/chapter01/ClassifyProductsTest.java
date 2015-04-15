package org.drools.devguide.chapter01;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.drools.devguide.BaseTest;
import org.drools.devguide.eshop.model.Product;
import org.drools.devguide.eshop.model.Product.Category;
<<<<<<< Updated upstream:chapter-01/chapter-01-tests/src/test/java/ClassifyProductsTest.java
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
=======
import static org.junit.Assert.assertEquals;
>>>>>>> Stashed changes:chapter-01/chapter-01-tests/src/test/java/org/drools/devguide/chapter01/ClassifyProductsTest.java
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class ClassifyProductsTest extends BaseTest {

    @Test
    public void simpleClassification() {
        KieSession kSession = createDefaultSession();
        Product p = new Product("A", 123.0, 234.0);
        kSession.insert(p);
        int fired = kSession.fireAllRules();
        assertThat(1, is(fired));
        assertThat(Category.LOW_RANGE, is(p.getCategory()));
    }
}

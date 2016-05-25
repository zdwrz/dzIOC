package com.aweiz.dzioc.test;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzioc.test.scannedPackage.TestController;
import com.aweiz.dzioc.test.scannedPackage.TestDAO;
import com.aweiz.dzioc.test.scannedPackage.TestDAOInterface;
import com.aweiz.dzioc.test.scannedPackage.TestService;
import com.aweiz.dzioc.test.scannedPackage.aopTestPackage.TestBean;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by daweizhuang on 5/16/16.
 */
public class SanityTest {

    static BeanFactory bf;
    @BeforeClass
    public static void prepare() throws Exception {
        bf = new BeanFactory(TestConfigure.class);
    }

    @Test
    public void getBeanByTypeTest() throws Exception {
        TestController tc = bf.getBean(TestController.class);
        assertTrue(tc != null);
    }

    @Test
    public void getBeanByNameTest() throws Exception {
        TestController tc = bf.getBean("controller",TestController.class);
        assertTrue(tc != null);
    }

    @Test
    public void checkDependency() throws Exception {
        TestController tc = bf.getBean("controller",TestController.class);
        assertTrue(tc.getService() != null);
    }

    @Test
    public void checkInterfaceInjection() throws Exception {
        TestService tc = bf.getBean("service",TestService.class);
        tc.getDao().doSomethingInDAO();
        assertTrue(tc.getDao() != null);
    }

    @Test
    public void stringInjectionTest() throws Exception {
        TestController tc = bf.getBean("controller",TestController.class);
        //System.out.println(tc.getNameController());
        assertTrue(tc.getNameController() != null);
    }

    @Test
    public void aopTest() throws Exception {
        TestBean tc = bf.getBean("testBean",TestBean.class);
        String res = tc.sayHi("Dawei");
        //System.out.println(res);
        assertTrue(res != null);
    }


}

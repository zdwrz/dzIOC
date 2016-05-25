package com.aweiz.dzioc.test;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzioc.test.scannedPackage.TestController;
import com.aweiz.dzioc.test.scannedPackage.TestDAO;
import com.aweiz.dzioc.test.scannedPackage.TestDAOInterface;
import com.aweiz.dzioc.test.scannedPackage.TestService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

/**
 * Created by daweizhuang on 5/16/16.
 */
public class SanityTest2 {


    @Test
    public void stringInjectionTest() throws Exception {
        Method m1 = TestDAOInterface.class.getDeclaredMethod("doSomethingInDAO");
        Method m2 = TestDAO.class.getDeclaredMethod("doSomethingInDAO");
        System.out.println(m1);
        System.out.println(m2);
      //  assertTrue (m1.equals(m2));
    }
}

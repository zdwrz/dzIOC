package com.aweiz.dzioc.test.scannedPackage;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzInject;

/**
 * Created by daweizhuang on 5/16/16.
 */
@DzBean("service")
public class TestService {

    @DzInject()
    private TestDAOInterface dao;

    public TestDAOInterface getDao() {
        return dao;
    }
}

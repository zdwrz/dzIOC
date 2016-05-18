package com.aweiz.dzioc.test.scannedPackage;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzInject;

/**
 * Created by daweizhuang on 5/16/16.
 */
@DzBean("controller")
public class TestController {
    static {

    }
    {

    }
    private String nameController = "123";

    @DzInject
    private TestService service;

    protected int iController = 1;


    private static final int HI_Controller=0;

    public TestService getService(){
        return this.service;
    }
}

package com.aweiz.dzioc.test.scannedPackage;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzInject;

/**
 * Created by daweizhuang on 5/16/16.
 */
@DzBean(value = "controller", isController = true)
public class TestController {
    static {

    }
    {

    }

    @DzInject
    private String nameController ;

    @DzInject
    private TestServiceInterface service;

    protected int iController = 1;

    private static final int HI_Controller=0;

    public TestServiceInterface getService(){
        return this.service;
    }

    public String getNameController() {
        return nameController;
    }
}

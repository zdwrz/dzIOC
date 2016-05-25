package com.aweiz.dzioc.test.scannedPackage.aopTestPackage;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzInject;

/**
 * Created by daweizhuang on 5/25/16.
 */
@DzBean("testBean")
public class TestBean {
    @DzInject
    private TestInterface tif;

    public String sayHi(String s){
        return tif.hello(s);
    }
}

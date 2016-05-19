package com.aweiz.dzioc.test;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzConfigure;

/**
 * Created by daweizhuang on 5/18/16.
 */
@DzConfigure(packagesToScan = {"com.aweiz.dzioc.test.scannedPackage"})
public class TestConfigure {

    @DzBean
    public String getTheString(){
        return "hahahahaha";
    }

    @DzBean("stringBean")
    public String getTheString2(){
        return "hooooooo~";
    }
}

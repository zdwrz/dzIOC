package com.aweiz.dzioc.test.scannedPackage.aopTestPackage;

import com.aweiz.dzioc.annotations.DzBean;

/**
 * Created by daweizhuang on 5/25/16.
 */
@DzBean
public class TestInterfaceImpl implements TestInterface {
    @Override
    public String hello(String str) {
        return "Hi how are you? " + str;
    }
}

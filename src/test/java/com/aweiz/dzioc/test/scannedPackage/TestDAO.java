package com.aweiz.dzioc.test.scannedPackage;

import com.aweiz.dzioc.annotations.DzBean;

/**
 * Created by daweizhuang on 5/16/16.
 */
@DzBean("DAO")
public class TestDAO implements TestDAOInterface{

    public void doSomethingInDAO() {
        System.out.println("do le something");
    }
}

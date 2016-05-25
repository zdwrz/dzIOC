package com.aweiz.dzioc.test.scannedPackage.aopTestPackage;

import com.aweiz.dzaop.Advice;
import com.aweiz.dzaop.ProcessPoint;
import com.aweiz.dzaop.annotations.DzAdvice;
import com.aweiz.dzaop.annotations.DzAspect;

import static com.sun.tools.classfile.AccessFlags.Kind.Method;

/**
 * Created by daweizhuang on 5/24/16.
 */
@DzAspect
public class TestAspect {

    @DzAdvice(advice = Advice.BEFORE, pointCut = "123" )
    public void doAOP(ProcessPoint pp){

        pp.proceed();

    }
}

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

    @DzAdvice(advice = Advice.BEFORE, pointCut = "doSome" )
    public Object doAOP(ProcessPoint pp){
        System.out.println("in aspect here we go");
        Object ret = pp.proceed();
        System.out.println("after aspect here we go");
        return ret;
    }
    @DzAdvice(advice = Advice.BEFORE, pointCut = "hello" )
    public Object doAOP2(ProcessPoint pp){
        System.out.println("in aspect2 here we go");
        Object ret = pp.proceed();
        System.out.println("after aspect2 here we go");
        return ret;
    }
}

package com.aweiz.dzaop;

import java.lang.reflect.Method;

/**
 *
 * Target Object.
 * Proxy Object.
 * Aspect Object - The Bean with @DzAspect
 *
 * method is the method defined in the Aspect Object.
 * Created by daweizhuang on 5/24/16.
 *
 */
public class Aspect {
    private Advice advice;
    private Method method;
    private Object aspectObj;

    public Object getAspectObj() {
        return aspectObj;
    }

    public void setAspectObj(Object aspectObj) {
        this.aspectObj = aspectObj;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

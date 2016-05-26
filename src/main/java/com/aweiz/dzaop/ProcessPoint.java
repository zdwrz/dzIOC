package com.aweiz.dzaop;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Currently the AOP doesn't support multiple layers. Only one Aspect will be accepted and only one proceed.
 * Created by daweizhuang on 5/24/16.
 */
public class ProcessPoint {
    private static Logger LOGGER = Logger.getLogger(ProcessPoint.class);
    private Object target;
    private Method method;
    private Object proxy;
    private Object[] args;
    public ProcessPoint(Object obj, Method method, Object proxy, Object[] args) {
        this.target = obj;
        this.method = method;
        this.proxy = proxy;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object getProxy() {
        return proxy;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object proceed() {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return null;

    }
}

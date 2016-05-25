package com.aweiz.dzaop;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by daweizhuang on 5/24/16.
 */
public class DynamicProxyHandler implements InvocationHandler {

    private static Logger LOGGER = Logger.getLogger(DynamicProxyHandler.class);

    private Object obj;

    private DynamicProxyHandler(Object obj) {
        this.obj = obj;
    }

    public static Object newInstance(Object obj){
        Class[] interfaces = obj.getClass().getInterfaces();
        if(interfaces == null || interfaces.length == 0){
            LOGGER.debug(obj + " doesn't have interfaces, no proxy can be create...");
            return obj;
        } else if (!ProxyChecker.checkObjectTobeProxy(obj)) {
            LOGGER.debug(obj + " is not allowed to create proxy, checked by ProxyChecker");
            return obj;
        }
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces, new DynamicProxyHandler(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = null;
        try {
            LOGGER.debug("before method :" + method+ " is called");

            if(AOPMethodAdvisor.containsMethod(method)){
                LOGGER.debug("AOP - Hit");
                Aspect a = AOPMethodAdvisor.getAspect(method);
                ret = AOPExecutor.process(new ProcessPoint(obj,method,proxy,args),a);
            }else {
                //no aspect defined
                ret = method.invoke(obj, args);
            }
            LOGGER.debug("after method :" + method+ " is called");
        }
        catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }
}
package com.aweiz.dzaop;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create a Dynamic proxy for the implementation class from the given interface.
 * Created by daweizhuang on 5/24/16.
 */
public class DynamicProxyHandler implements InvocationHandler {

    private static Logger LOGGER = Logger.getLogger(DynamicProxyHandler.class);

    /**
     * This is the object of the target class.
     */
    private Object obj;

    /**
     * This private constructor will be used in newInstance() method.
     * @param obj
     */
    private DynamicProxyHandler(Object obj) {
        this.obj = obj;
    }

    /**
     * A helper method to create a proxy of the given object.
     * A {@link com.aweiz.dzaop.ProxyChecker ProxyChecker} is used to check if there are any exclusion for the proxy, like the a String bean is not allowed to create a proxy.
     * @param obj target object.
     * @return proxy of the obj.
     */
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

    /**
     * This is the wrapper method of the target method.
     * {@link com.aweiz.dzaop.AOPMethodAdvisor AOPMethodAdvisor} will be used to get the aspect of the method.
     * If there is no aspect defined for this method, the original method will be executed.
     * If there is aspect defined for this method, {@link com.aweiz.dzaop.AOPExecutor AOPExecutor} will be used to process the Aspect.
     * @param proxy the proxy obj.
     * @param method target method
     * @param args arguments of the target method
     * @return the return of the target method.
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = null;
        try {
            LOGGER.debug("before method :" + method+ " is called");
            Aspect a = null;
            if((a = AOPMethodAdvisor.getAspect(method)) != null){
                LOGGER.debug("AOP - Hit");
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

package com.aweiz.dzaop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by daweizhuang on 5/24/16.
 */
public class AOPMethodAdvisor {
    private static Map<Method, Aspect> advices = new ConcurrentHashMap<>();

    private AOPMethodAdvisor(){}

    public static boolean containsMethod(Method method) {
        return advices.containsKey(method);
    }
    public static Aspect getAspect(Method method) {
        return null;
    }
    public static Map<Method, Aspect> getAdvices() {
        return advices;
    }

}

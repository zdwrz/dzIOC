package com.aweiz.dzaop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * To store all aspect in a map.
 * To find aspect for a particular method.
 * Created by daweizhuang on 5/24/16.
 */
public class AOPMethodAdvisor {
    private static Map<String, Aspect> advices = new ConcurrentHashMap<>();

    private AOPMethodAdvisor(){}
//
//    public static boolean containsMethod(Method method) {
//        return advices.containsKey(method);
//    }
    public static Aspect getAspect(Method method) {
        String methodName = method.getName();
        for (String s : advices.keySet()) {
            if (methodName.contains(s)) {
                return advices.get(s);
            }
        }
        return null;
    }
    public static Map<String, Aspect> getAdvices() {
        return advices;
    }

}

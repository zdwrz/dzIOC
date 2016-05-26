package com.aweiz.dzaop;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * Created by daweizhuang on 5/24/16.
 */
public class AOPExecutor {
    public static Object process(ProcessPoint processPoint, Aspect a) {
        try {
            return a.getMethod().invoke(a.getAspectObj(),processPoint);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

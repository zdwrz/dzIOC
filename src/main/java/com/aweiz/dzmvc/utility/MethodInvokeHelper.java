package com.aweiz.dzmvc.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by daweizhuang on 2/7/17.
 */
public class MethodInvokeHelper {
    public static Object invoke(Method method, Object obj, Map<String,Object> paramMap) throws InvocationTargetException, IllegalAccessException {
        Object[] parameterList = new Object[method.getParameterCount()];

        return method.invoke(obj,parameterList);
    }
}

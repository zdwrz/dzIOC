package com.aweiz.dzmvc;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzmvc.request.WebRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daweizhuang on 2/2/17.
 */
public class DzWebContext {
    private BeanFactory beanFactory = null;
    private Map<WebRequest, Map.Entry<Method,Object>> handlerMapping = new HashMap<>();

    public void initializeWebContext(BeanFactory bf){
        beanFactory = bf;
        initHandlerMapping(beanFactory.getControllers());
    }

    private void initHandlerMapping(Map<Class, Object> controllers) {
        //TODO
    }

    public Map.Entry<Method,Object> getHandler(WebRequest request){
        Map.Entry<Method,Object> methodCall = handlerMapping.get(request);
        return methodCall;
    }
}

package com.aweiz.dzmvc;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzmvc.request.WebRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daweizhuang on 2/2/17.
 * A Context of all controller mapping. Converter. Parsers.
 */
public class DzWebContext {
    /**
     * Reference to the main bean factory
     */
    private BeanFactory beanFactory = null;
    /**
     * HanlderMapping from the http request to a specific controller method
     */
    private Map<WebRequest, Map.Entry<Method,Object>> handlerMapping = new HashMap<>();

    /**
     * Bean factory should be initialized in the DzWebContextListener
     * @param bf
     */
    public void initializeWebContext(BeanFactory bf){
        beanFactory = bf;
        initHandlerMapping(beanFactory.getControllers());
    }

    /**
     * Scan all controllers class and map the URL to method.
     * @param controllers
     */
    private void initHandlerMapping(Map<Class, Object> controllers) {
        //TODO

    }

    public Map.Entry<Method,Object> getHandler(WebRequest request){
        Map.Entry<Method,Object> methodCall = handlerMapping.get(request);
        return methodCall;
    }
}

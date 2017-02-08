package com.aweiz.dzmvc;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzmvc.annotations.DzMapping;
import com.aweiz.dzmvc.exceptions.URLMappingException;
import com.aweiz.dzmvc.request.WebRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daweizhuang on 2/2/17.
 * A Context of all controller mapping. Converter. Parsers.
 */
public class DzWebContext {
    private static Logger LOGGER = Logger.getLogger(DzWebContext.class);
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
        for (Map.Entry<Class, Object> entry : controllers.entrySet()) {
            Class clazz = entry.getKey();
            Object controllerObj = entry.getValue();
            for (Method m : clazz.getDeclaredMethods()) {
                if(m.getReturnType() == void.class){
                    throw new URLMappingException("The controller :" + m + " returns VOID?");
                }
                Annotation[] ann = m.getDeclaredAnnotations();
                for(Annotation a : ann) {
                    if (a instanceof DzMapping) {
                        DzMapping mappingAnn = (DzMapping) a;
                        String url = mappingAnn.value();
                        if (StringUtils.isEmpty(url)) {
                            throw new URLMappingException("The controller :" + m + " doesn't have any URL mapped.");
                        }
                        String httpMethod = mappingAnn.httpMethod();
                        if(httpMethod == null || httpMethod.equals("")){
                            httpMethod = "ALL";
                        }
                        WebRequest wr = new WebRequest(url,httpMethod);
                        m.setAccessible(true);
                        Map.Entry<Method,Object> handler = new HashMap.SimpleEntry<>(m,controllerObj);
                        if(handlerMapping.containsKey(wr)){
                            LOGGER.warn("The controller :" + m +" uses the URL mapping: " + wr.getUrl() + ". This may be caused by using same URL but different HttpMethod");
                        }
                        handlerMapping.put(wr, handler);
                    }
                }
            }
        }
        handlerMapping.forEach((k,v) -> LOGGER.debug(k.getUrl() +" " +k.getHttpMethod() + " : " + v.getKey()));
    }

    public Map.Entry<Method,Object> getHandler(WebRequest request){
        Map.Entry<Method,Object> methodCall = handlerMapping.get(request);
        return methodCall;
    }
}

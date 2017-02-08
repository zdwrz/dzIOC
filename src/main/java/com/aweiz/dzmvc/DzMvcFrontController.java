package com.aweiz.dzmvc;

import com.aweiz.dzmvc.request.WebRequest;
import com.aweiz.dzmvc.utility.MethodInvokeHelper;
import com.aweiz.dzmvc.utility.ResponseJsonParse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * This class acts as DispatcherServlet.
 * It provides the ability to scan all the {@link com.aweiz.dzioc.annotations.DzBean @DzBean} class for URL mapping<br>
 *
 * //TODO Scan for HandlerMapping,Parse Param, Review Resolver-JSP, REST-Support JSON.
 *
 * Created by daweizhuang on 2/1/17.
 */
public class DzMvcFrontController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(DzMvcFrontController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Request Basic Info: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("FC doGET");
        DzWebContext context = (DzWebContext) this.getServletContext().getAttribute("WebContext");
        WebRequest request = new WebRequest(req.getRequestURI(), req.getMethod());
        Map.Entry<Method,Object> methodCall = context.getHandler(request);
        if (methodCall == null) {//if no exact match, find URL match
            methodCall = context.getHandler(new WebRequest(req.getRequestURI(), "ALL"));
        }
        Map<String, Object> paramMap = null;
        Object rtn = null;
        try {
            //TODO Parameter Parsing then invoke the controller
            //TODO JSON conversion
            rtn = MethodInvokeHelper.invoke(methodCall.getKey(), methodCall.getValue(), paramMap);
            LOGGER.debug("Controller returns with :" + rtn);
            if (rtn instanceof String) {
                //TODO JSP resolver
            }else{
                //JSON conversion from Object to JSON and put in response
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                ResponseJsonParse parser = new ResponseJsonParse();
                String respJson = parser.parse(rtn);
                resp.setContentLength(respJson.length());
                resp.getWriter().write(respJson);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e){
            LOGGER.error("Cannot parse the object to Json :" + rtn);
            e.printStackTrace();
        } catch(Exception e){
            LOGGER.error("Exception is  :" , e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

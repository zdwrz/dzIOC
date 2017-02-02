package com.aweiz.dzmvc;

import com.aweiz.dzmvc.request.WebRequest;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * This class acts as DispatcherServlet.
 * It provides the ability to scan all the {@link com.aweiz.dzioc.annotations.DzBean @DzBean} class for URL mapping<br>
 *
 * //TODO Scan for HandlerMapping,Parse Param, Review Resolver-JSP only, REST-Support.
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
        LOGGER.info("Hit FC doGET");
        DzWebContext context = (DzWebContext) this.getServletContext().getAttribute("WebContext");
        WebRequest request = new WebRequest();
        Map.Entry<Method,Object> methodCall = context.getHandler(request);
        try {
            ((Method)(methodCall.getValue())).invoke(methodCall.getValue());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

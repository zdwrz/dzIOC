package com.aweiz.dzioc.test.web;

import com.aweiz.dzmvc.request.WebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by daweizhuang on 2/7/17.
 */
public class JacksonTest {
    ObjectMapper mapper = new ObjectMapper();
    String s = null;

    @Test
    public void testMap() throws IOException {
        WebRequest wr = new WebRequest("123");
        s = mapper.writeValueAsString(wr);
        System.out.println(s);
        testMap2();
    }

    public void testMap2() throws IOException {
        if (s != null) {
            WebRequest wr = mapper.readValue(s, WebRequest.class);
            System.out.println(wr);
        }
    }

}

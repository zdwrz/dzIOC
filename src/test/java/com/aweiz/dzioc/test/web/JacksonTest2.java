package com.aweiz.dzioc.test.web;

import com.aweiz.dzmvc.request.WebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by daweizhuang on 2/7/17.
 */
public class JacksonTest2 {
    ObjectMapper mapper = new ObjectMapper();
    String s = "{\"name\":\"dawei\", \"idd\":1, \"id\":1}";

    @Test
    public void testMap() throws IOException {
        SomePojo sp = mapper.readValue(s, SomePojo.class);
        System.out.println(sp);
    }

}

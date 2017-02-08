package com.aweiz.dzmvc.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by daweizhuang on 2/7/17.
 */
public class ResponseJsonParse {
    private ObjectMapper mapper = new ObjectMapper();
    public String parse(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}

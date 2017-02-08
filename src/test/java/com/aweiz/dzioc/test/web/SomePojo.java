package com.aweiz.dzioc.test.web;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daweizhuang on 2/7/17.
 */
public class SomePojo {
    private String name;
    private Integer id;
    private boolean left;
    private Map<String, Object> anyOther = new HashMap<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    @JsonAnyGetter
    public Map<String,Object> any() {
        return anyOther;
    }
    @JsonAnySetter
    public void set(String name, Object value) {
        anyOther.put(name, value);
    }

    @Override
    public String toString() {
        return "SomePojo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", left=" + left +
                '}';
    }
}

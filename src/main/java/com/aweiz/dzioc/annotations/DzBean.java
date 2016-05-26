package com.aweiz.dzioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define a container managed bean.
 * Put on a class or a method in configuration class.
 * Created by daweizhuang on 5/16/16.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DzBean {
    String value() default "";
}

package com.aweiz.dzaop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To define a class as aspect. Inside the class should have multiple {@link com.aweiz.dzaop.annotations.DzAdvice @DzAdvice} annotated method
 * Created by daweizhuang on 5/24/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DzAspect {
}

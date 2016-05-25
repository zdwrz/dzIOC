package com.aweiz.dzaop.annotations;

import com.aweiz.dzaop.Advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by daweizhuang on 5/24/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DzAdvice {
    Advice advice() default Advice.BEFORE;
    String[] pointCut();
}

package com.aweiz.dzmvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by daweizhuang on 2/2/17.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DzMapping {
    String value() default "";
    boolean isRest() default false;
    String httpMethod() default "";
    String produce() default "";
}

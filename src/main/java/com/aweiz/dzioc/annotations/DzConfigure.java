package com.aweiz.dzioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Put on a class to define a configuration class. Only one configuration file is allowed.
 * Created by daweizhuang on 5/18/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DzConfigure {
    String[] packagesToScan() default {};
}

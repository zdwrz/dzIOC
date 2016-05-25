package com.aweiz.dzaop;

/**
 * Created by daweizhuang on 5/24/16.
 */
public class ProxyChecker {
    private ProxyChecker(){}

    static boolean checkObjectTobeProxy(Object object) {
        boolean canHaveProxy = true;
        if (object instanceof String) {
            canHaveProxy = false;
        }
        return canHaveProxy;
    }
}

package com.tigger.security;

import java.util.Arrays;
import java.util.List;

/**
 * Created by laibao
 */
public interface Const {

    /**
     * 要校验的Http Method
     */
    List<String> FILTER_METHODS = Arrays.asList("POST" , "PUT" , "DELETE");

    /**
     * cookie中token的名称
     */
    String CSRF_TOOKEN_COOKIE_NAME = "__csrf_token__" ;

    /**
     * request中token的名称
     */
    String CSRF_TOKEN_REQUEST_NAME = "__csrf_token__";

    /**
     * 一天的秒数
     */
    int ONE_DAY_SECONDS = 24 * 60 * 60 ;

}

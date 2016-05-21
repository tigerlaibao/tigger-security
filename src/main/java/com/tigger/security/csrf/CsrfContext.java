package com.tigger.security.csrf;

import com.tigger.security.Const;
import com.tigger.security.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laibao
 */
public class CsrfContext {

    public static String getCsrfToken(HttpServletRequest request){
       return CookieUtil.getCookieValue(request, Const.CSRF_TOOKEN_COOKIE_NAME);
    }

}

package com.tigger.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by laibao
 */
public class CookieUtil {

    public static String getCookieValue(HttpServletRequest request , String cookieName){
        if(request == null || cookieName == null ){
            return null ;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null ;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void setCookie(HttpServletResponse response , String cookieName , String cookieValue , int maxAgeSeconds){
        if(cookieName == null){
            return ;
        }
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response , String cookieName){
        setCookie(response , cookieName , null , 0);
    }

}

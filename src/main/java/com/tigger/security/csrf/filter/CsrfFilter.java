package com.tigger.security.csrf.filter;

import com.tigger.security.Const;
import com.tigger.security.util.CookieUtil;
import com.tigger.security.util.RandomTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 放Csrf攻击Filter
 * Created by laibao
 */
public class CsrfFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(CsrfFilter.class);

    private List<String> filterMethods ;

    public void init(FilterConfig filterConfig) throws ServletException {
        String filterMethods = filterConfig.getInitParameter("filterMethods");
        if(filterMethods == null){
            this.filterMethods = Arrays.asList("POST");
        }else{
            this.filterMethods = Arrays.asList(filterMethods.toUpperCase().split(","));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean pass = checkCsrfToken(request,response);
        if(pass){
            chain.doFilter(request, response);
        }else{
            outputForbiddenResponse(request ,response);
        }
    }

    protected boolean checkCsrfToken(ServletRequest request,ServletResponse response){
        if (!(request instanceof HttpServletRequest)) {
            logger.warn("request {} is not a HttpServletRequest instance , [pass]", request);
            return true;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String cookieCsrfToken = CookieUtil.getCookieValue(httpRequest, Const.CSRF_TOOKEN_COOKIE_NAME);
        if (cookieCsrfToken == null || "".equals(cookieCsrfToken.trim())) {
            logger.warn("cookie csrf_token is null , [pass]");
            setCsrfToken(response);
            return true;
        }
        if (!filterMethods.contains(httpRequest.getMethod().toUpperCase())) {
            return true;
        }
        String requestCsrfToken = httpRequest.getParameter(Const.CSRF_TOKEN_REQUEST_NAME);
        if (!cookieCsrfToken.equals(requestCsrfToken)) {
            logger.warn("request csrf_token[{}] & cookie csrf_token[{}] not equals ,mybe is a attack ,[block]", requestCsrfToken, cookieCsrfToken);
            return false ;
        }
        return true ;
    }

    protected void setCsrfToken(ServletResponse response){
        if (!(response instanceof HttpServletResponse)) {
            logger.warn("set csrfToken failed ,response {} is not a HttpServletResponse instance", response);
            return ;
        }
        String token = RandomTokenGenerator.generate() ;
        CookieUtil.setCookie((HttpServletResponse) response, Const.CSRF_TOOKEN_COOKIE_NAME,token, Const.ONE_DAY_SECONDS);
    }

    protected void outputForbiddenResponse(ServletRequest request, ServletResponse response){
        if(!(response instanceof HttpServletResponse)){
            return ;
        }
        try {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN , "forbidden");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void destroy() {

    }
}

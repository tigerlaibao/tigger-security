package com.tigger.security.csrf.support.jsp;

import com.tigger.security.csrf.CsrfContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * 输出csrfToken的值
 * Created by laibao
 */
public class CsrfTokenTag extends BodyTagSupport {

    Logger logger = LoggerFactory.getLogger(CsrfTokenTag.class);

    @Override
    public int doStartTag() throws JspException {
        if(!(pageContext.getRequest() instanceof HttpServletRequest)){
            logger.warn("output csrf token failed , request[{}] is not a HttpServletRequest instance"
                    , pageContext.getRequest());
            return super.doStartTag();
        }
        String token = CsrfContext.getCsrfToken((HttpServletRequest)pageContext.getRequest());
        try {
            pageContext.getOut().write(token);
        } catch (IOException e) {
            logger.error(e.getMessage() ,e);
        }
        return super.doStartTag();
    }

}

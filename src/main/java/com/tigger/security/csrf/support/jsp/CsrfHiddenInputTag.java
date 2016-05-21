package com.tigger.security.csrf.support.jsp;

import com.tigger.security.Const;
import com.tigger.security.csrf.CsrfContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * 输出csrf的一个hidden的input
 * 类似：<input type="hidden" name="__csrf_token__" value="xxx" />
 * Created by laibao
 */
public class CsrfHiddenInputTag extends BodyTagSupport {

    Logger logger = LoggerFactory.getLogger(CsrfTokenTag.class);

    @Override
    public int doStartTag() throws JspException {
        if(!(pageContext.getRequest() instanceof HttpServletRequest)){
            logger.warn("output csrf token failed , request[{}] is not a HttpServletRequest instance"
                    , pageContext.getRequest());
            return super.doStartTag();
        }
        String token = CsrfContext.getCsrfToken((HttpServletRequest) pageContext.getRequest());
        try {
            StringBuilder hiddenInputHtml = new StringBuilder();
            hiddenInputHtml.append("<input type=\"hidden\" name=\"");
            hiddenInputHtml.append(Const.CSRF_TOKEN_REQUEST_NAME);
            hiddenInputHtml.append("\" value=\"");
            hiddenInputHtml.append(token);
            hiddenInputHtml.append("\" />");
            pageContext.getOut().write(hiddenInputHtml.toString());
        } catch (IOException e) {
            logger.error(e.getMessage() ,e);
        }
        return super.doStartTag();
    }

}

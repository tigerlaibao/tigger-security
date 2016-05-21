package com.tigger.security.csrf.support.velocity;

import com.tigger.security.Const;
import com.tigger.security.csrf.CsrfContext;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * Velocity自定义标签输出csrf token
 * Created by laibao
 */
public class CsrfTokenTag extends Directive {

    private static final String RENDER_TYPE_TOKEN = "TOKEN";
    private static final String RENDER_TYPE_HIDDENINPUT = "HIDDENINPUT";
    private static final List<String> RENDER_TYPES = Arrays.asList(RENDER_TYPE_TOKEN ,RENDER_TYPE_HIDDENINPUT);

    @Override
    public String getName() {
        return "csrf";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        if(node.jjtGetNumChildren() <= 0){
            throw new RuntimeException("render不能为空");
        }
        SimpleNode renderNode = (SimpleNode) node.jjtGetChild(0);
        String render = (String)renderNode.value(context);
        if(render == null || !RENDER_TYPES.contains(render.toUpperCase().trim())){
            throw new IllegalArgumentException("render must in [token , hiddenInput]");
        }
        render = render.trim();
        ViewToolContext viewToolContext = (ViewToolContext)context.getInternalUserContext();
        HttpServletRequest request = viewToolContext.getRequest();
        String token = CsrfContext.getCsrfToken(request);
        String renderHtml = null ;
        if(RENDER_TYPE_TOKEN.equalsIgnoreCase(render)){
            renderHtml = token;
        }else if(RENDER_TYPE_HIDDENINPUT.equalsIgnoreCase(render)){
            StringBuilder hiddenInputHtml = new StringBuilder();
            hiddenInputHtml.append("<input type=\"hidden\" name=\"");
            hiddenInputHtml.append(Const.CSRF_TOKEN_REQUEST_NAME);
            hiddenInputHtml.append("\" value=\"");
            hiddenInputHtml.append(token);
            hiddenInputHtml.append("\" />");
            renderHtml = hiddenInputHtml.toString();
        }
        if(renderHtml != null) {
            writer.write(renderHtml);
        }
        return true;
    }

}

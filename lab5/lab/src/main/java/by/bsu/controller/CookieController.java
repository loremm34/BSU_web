package by.bsu.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;
import java.util.Calendar;

public class CookieController implements IController {

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        ctx.setVariable("lastVisit", webExchange.getAttributeValue("lastVisit"));
        ctx.setVariable("visitCount", webExchange.getAttributeValue("visitCount"));
        templateEngine.process("cookies", ctx, writer);
    }

}

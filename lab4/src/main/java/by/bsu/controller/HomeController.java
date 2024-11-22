package by.bsu.controller;

import java.io.Writer;
import java.util.Calendar;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

public class HomeController implements IController {

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        ctx.setVariable("today", Calendar.getInstance());
        ctx.setVariable("lastVisit", webExchange.getAttributeValue("lastVisit"));
        ctx.setVariable("visitCount", webExchange.getAttributeValue("visitCount"));
        templateEngine.process("home", ctx, writer);

    }

}

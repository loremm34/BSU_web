package by.bsu.controller;

import by.bsu.entities.authentication.User;
import by.bsu.entities.authentication.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class HomeController implements IController {
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        if (!ctx.getExchange().getRequest().getRequestPath().equals("/home")) {
            response.sendRedirect("/home");
        }
        User u = (User) request.getSession(true).getAttribute("user");

        if (!u.getRole().equals(UserRole.GUEST)) {
            ctx.setVariable("username", u.getUsername());
        } else {
            ctx.setVariable("username", "Guest");
        }
        System.out.println(u.getRole().name());
        ctx.setVariable("role", u.getRole().name());
        templateEngine.process("home", ctx, writer);
    }
}
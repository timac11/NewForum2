/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.LoginBean;


/**
 *
 * @author User
 */
public class LoginFilter implements Filter{

    public static final String LOGIN_PAGE="/login.xhtml";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        LoginBean session = (LoginBean) req.getSession().getAttribute("loginBean");
        String url = req.getRequestURI();
        if(session==null || !session.getIsLogged()){
                resp.sendRedirect(req.getServletContext().getContextPath()+ LOGIN_PAGE);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}

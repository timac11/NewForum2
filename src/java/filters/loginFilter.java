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
import beans.loginBean;


/**
 *
 * @author User
 */
public class loginFilter implements Filter{

    public static final String LOGIN_PAGE="/login.xhtml";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        loginBean session = (loginBean) req.getSession().getAttribute("loginBean");
        String url = req.getRequestURI();
        /*
        A. If request for forum or logout and there's no session, redirect the request to login.xhtml
        B. If request for signUp or login and there's session ,redirect the request to forum.xhtml
        C. If request for logout and there's session,remove the session and redirect to login.xhtml
        */
        if(session==null || !session.getIsLogged()){
            if(url.indexOf("forum.xhtml")>=0 || url.indexOf("logout.xhtml")>=0){
                resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
            }
            else chain.doFilter(request, response);
        }
        else{
            if(url.indexOf("signUp.xhtml")>=0 || url.indexOf("login.xhtml")>=0){
                resp.sendRedirect(req.getServletContext().getContextPath()+"/forum.xhtml");
            }
            else if(url.indexOf("logout.xhtml") >=0){
                //req.getSession().removeAttribute("loginBean");
                session.logout();
                resp.sendRedirect(req.getServletContext().getContextPath()+"/index.xhtml");
            }
            else{
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }
    
}

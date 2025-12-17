package com.foodcart.project3.filter;

import com.foodcart.project3.refreshtoken.refreshtoken;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class cookiefilter implements Filter {
    @Autowired
    private refreshtoken refreshtoken;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;


        if("OPTIONS".equalsIgnoreCase(req.getMethod())){
            resp.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req,resp);
            return;
        }

        String path=req.getRequestURI();

        if(path.contains("/actions/login") || path.contains("/actions/verify") || path.contains("/iterms/getiterms")|| path.contains("/iterms/additerm") || path.startsWith("/itermsimage/") || path.contains("/actions/getlogged") || path.contains("/actions/logout") || path.contains("/actions/iterms/{catararies}")){
            filterChain.doFilter(req,resp);
            return;
        }

        Cookie[] cookies=req.getCookies();
        String refresh=null;

        if(cookies!=null){
            for (Cookie c:cookies){
                if("refresh".equals(c.getName())){
                    refresh=c.getValue();
                }
            }
        }

        if(refresh!=null && refreshtoken.verifyrefreshtokens(refresh)) {
            String email = refreshtoken.extractemails(refresh);
            req.setAttribute("emails", email);

            String access = refreshtoken.accesstoken(email);
            resp.setHeader("access", access);
        }
            filterChain.doFilter(req,resp);

        }


    }





// if(path.contains("/actions/login") || path.contains("/actions/verify") || path.contains("/iterms/getiterms")|| path.contains("/iterms/additerm") || path.startsWith("/itermsimage/") || path.contains("/actions/getlogged") || path.contains("/actions/logout")){
//        filterChain.doFilter(req,resp);
//            return;
//                    }
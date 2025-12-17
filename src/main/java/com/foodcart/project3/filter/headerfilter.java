package com.foodcart.project3.filter;

import com.foodcart.project3.refreshtoken.refreshtoken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class headerfilter implements Filter {
    @Autowired
    private refreshtoken refreshtoken;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;

       String path=req.getRequestURI();
        if(path.contains("/actions/login") || path.contains("/actions/verify") || path.contains("/iterms/getiterms") || path.contains("/iterms/additerm") || path.startsWith("/itermsimage/") || path.contains("/actions/getlogged") || path.contains("/actions/logout" ) || path.contains("/actions/iterms/{catararies}")){
            filterChain.doFilter(req,resp);
            return;
        }


        if("OPTIONS".equalsIgnoreCase(req.getMethod())){
            resp.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req,resp);
            return;
        }

        String header=req.getHeader("Authorization");
//        if(header != null){
//            header=header.trim();
//        }

        if( header!=null  && header.startsWith("Bearer ")){
            String substring=header.substring(7);
            if(refreshtoken.verifyaccesstokens(substring)){
                String email= refreshtoken.extractemail(substring);
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(email,null, List.of());

                SecurityContextHolder.getContext().setAuthentication(auth);

                req.setAttribute("email",email);
            }
                filterChain.doFilter(req,resp);


        }


    }
}

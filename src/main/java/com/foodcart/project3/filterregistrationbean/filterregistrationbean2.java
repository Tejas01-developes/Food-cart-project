//package com.foodcart.project3.filterregistrationbean;
//
//import com.foodcart.project3.filter.cookiefilter;
//import com.foodcart.project3.filter.headerfilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class filterregistrationbean2 {
//    @Autowired
//    private cookiefilter cookiefilter;
//    @Bean
//    public FilterRegistrationBean<cookiefilter> filterRegistrationBean(cookiefilter cookiefilter){
//        FilterRegistrationBean<cookiefilter>registrationBean=new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(cookiefilter);
//        registrationBean.setEnabled(false);
//
//        return registrationBean;
//
//
//
//    }
//
//
//
//
//
//}

//package com.foodcart.project3.filterregistrationbean;
//
//import com.foodcart.project3.filter.headerfilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class filterregistrationbean {
//    @Autowired
//    private headerfilter headerfilter;
//
//
//    @Bean
//    public FilterRegistrationBean<headerfilter> filterRegistrationBean(headerfilter headerfilter){
//        FilterRegistrationBean<headerfilter>registrationBean=new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(headerfilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//
//
//
//    }
//
//
//
//
//}

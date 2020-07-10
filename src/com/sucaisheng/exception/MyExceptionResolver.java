package com.sucaisheng.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
//public class MyExceptionResolver implements HandlerExceptionResolver {
//    @Override
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        System.out.println("頁面走失");
//        String msg = "頁面走失";
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("msg", msg);
//        mav.setViewName("error");
//        return mav;
//    }
//}

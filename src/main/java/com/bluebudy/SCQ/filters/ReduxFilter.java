package com.bluebudy.SCQ.filters;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluebudy.SCQ.dtos.FunctionWsModelDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ReduxFilter implements HandlerInterceptor {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView)
            throws Exception {
        String reduceFunctiosHeader = request.getHeader("reduxFunctions");
        Optional<String> reduceFunctionsOpt = Optional.ofNullable(reduceFunctiosHeader);
        if (reduceFunctionsOpt.isPresent()) {
            String[] functions = reduceFunctionsOpt.get().split((","));
            if (response.getStatus() == 200) {
                System.out.println("funcoes chamadas " +  functions.toString());
                this.template.convertAndSend("/reducer/return",  new FunctionWsModelDTO("function", Arrays.asList(functions), null));
            }

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");

    }

}

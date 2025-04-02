package com.servletfilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PayrollFilter implements Filter {

    private List<String> data;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String excluded = filterConfig.getInitParameter("blockedendpoint");
        System.out.println(excluded + "here the exclude ");
        if (excluded != null) {
            data = Arrays.asList(excluded.split(", "));
            for (String endpoint : data) {

                System.out.println("Here the endpoint is " + endpoint);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("hi here payroll filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("here the request is " + request.getRequestURI());
        if (data.contains(request.getRequestURI())) {
            System.out.println("here endpoint is blocked");
            return;
        }
        System.out.println("here endpoint is Allowed");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

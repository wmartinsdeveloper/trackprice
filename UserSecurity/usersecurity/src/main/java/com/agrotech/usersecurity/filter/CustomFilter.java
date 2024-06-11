package com.agrotech.usersecurity.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("================================================");
        System.out.println(request.getServerName());
        System.out.println("================================================");

        HttpServletResponse res = (HttpServletResponse) response;

        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        // CsrfToken csrfToken = (CsrfToken)
        // request.getAttribute(CsrfToken.class.getName());
        // if (csrfToken.getHeaderName() != null) {
        // response.setHeader(csrfToken.getHeaderName(),csrfToken.getToken());
        // }

        filterChain.doFilter(request, response);

    }

}

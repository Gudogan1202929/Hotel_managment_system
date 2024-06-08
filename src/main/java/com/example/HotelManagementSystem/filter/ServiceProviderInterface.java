package com.example.HotelManagementSystem.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.ext.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Provider
@Component
public class ServiceProviderInterface implements Filter {

    private final ServiceProvider serviceProvider;

    @Autowired
    public ServiceProviderInterface(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        serviceProvider.filter(servletRequest, servletResponse, filterChain);
        serviceProvider.filter((HttpServletResponse) servletResponse);
    }
}

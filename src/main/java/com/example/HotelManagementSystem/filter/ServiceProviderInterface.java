package com.example.HotelManagementSystem.filter;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public interface ServiceProviderInterface extends ContainerRequestFilter, ContainerResponseFilter {

    @Transactional
    @Override
    void filter(ContainerRequestContext containerRequestContext);

    @Override
    void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext);
}

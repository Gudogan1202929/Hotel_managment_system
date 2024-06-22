package com.example.HotelManagementSystem.filter;

import com.example.HotelManagementSystem.user.dto.RollesAllowed;
import com.example.HotelManagementSystem.user.entity.LogFile;
import com.example.HotelManagementSystem.user.jwt.Check;
import com.example.HotelManagementSystem.user.service.LogFileService;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import com.example.HotelManagementSystem.utils.constant.SystemPaths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ServiceProvider {

    private final LogFileService logFileService;
    private final Check check;

    @Autowired
    public ServiceProvider(LogFileService logFileService, Check check) {
        this.logFileService = logFileService;
        this.check = check;
    }

    public void filter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("Request URL: " + request.getRequestURL());


        String ipAddress = request.getHeader(SystemConstants.IP_HEADER);
        String requestUri = request.getRequestURI();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.substring(7).trim();
            System.out.println(authorizationHeader);
        }

        boolean isAllowed = false;

        if ((requestUri.contains(SystemPaths.LOGIN_PATH))||
                //&& ipAddress != null) ||
                (requestUri.contains(SystemPaths.SIGNUP))
                        //&& ipAddress != null)
        || (requestUri.contains(SystemPaths.CHANGEPASSWORD))
                        // && ipAddress != null)
                         || (requestUri.contains(SystemPaths.CHANGEUSERNAME))
                        //&& ipAddress != null)
                        || (requestUri.contains(SystemPaths.CHANGEROLE))
                        //&& ipAddress != null )
                        || (requestUri.contains("user"))
                        || (requestUri.contains("/swagger-ui"))
                        || (requestUri.contains("/swagger-ui/swagger-initializer.js")) ||
                        (requestUri.contains("/v3/api-docs/swagger-config"))||
                                (requestUri.contains("/v3/api-docs"))){

            isAllowed = true;
        } else if (check.CheckJWTIfForUser(authorizationHeader)){
                // && ipAddress != null) {
            String roleOfToken = null;
            if (authorizationHeader != null) {
                roleOfToken = check.WhatRole(authorizationHeader);
            }
            List<RollesAllowed> listOfRolls = new ArrayList<>();

            RollesAllowed Admin = new RollesAllowed(SystemConstants.ADMIN);

            //CancellationRequest
            Admin.getPaths().add("/api/v1/CancellationRequest.*");

            //Customer
            Admin.getPaths().add("/api/v1/customers.*");

            //Employee
            Admin.getPaths().add("/api/v1/employees.*");

            //Housekeeping
            Admin.getPaths().add("/api/v1/housekeeping.*");

            //Invoice
            Admin.getPaths().add("/api/v1/invoices.*");

            //Reservation
            Admin.getPaths().add("/api/v1/reservations.*");

            //Room
            Admin.getPaths().add("/api/v1/rooms.*");


            listOfRolls.add(Admin);

            RollesAllowed User = new RollesAllowed(SystemConstants.USER);
            //CancellationRequest
            User.getPaths().add("/api/v1/CancellationRequest");
            User.getPaths().add("/api/v1/CancellationRequest/\\d+");
            User.getPaths().add("/api/v1/CancellationRequest/search");

            //Customer
            User.getPaths().add("/api/v1/customers.*");

            //Employee
            //not Authorized

            //Housekeeping
            //not Authorized

            //Invoice
            //not Authorized

            //Reservation
            User.getPaths().add("/api/v1/reservations");
            User.getPaths().add("/api/v1/reservations/\\d+");
            User.getPaths().add("/api/v1/reservations/search");
            User.getPaths().add("/api/v1/reservations/\\d+/request-cancellation");

            //Room
            //not Authorized


            listOfRolls.add(User);

            System.out.println(requestUri);

            for (RollesAllowed role : listOfRolls) {
                if (role.getRole().equalsIgnoreCase(roleOfToken)) {
                    for (String path : role.getPaths()) {
                        if (Pattern.matches(path, requestUri)) {
                            isAllowed = true;
                            break;
                        }
                    }
                }
            }

            if (!isAllowed) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
//        LogFile iPsModel = new LogFile(currentDateTime.toString(), ipAddress, requestUri);
        LogFile iPsModel = LogFile.builder()
                .time(currentDateTime.toString())
                .ip(ipAddress)
                .URL(requestUri)
                .build();
        logFileService.saveLog(iPsModel);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void filter(HttpServletResponse response) throws IOException {
        int statusCode = response.getStatus();
        String message;
        switch (statusCode) {
            case 200:
                message = SystemConstants.OK;
                break;
            case 201:
                message = SystemConstants.CREATED;
                break;
            case 204:
                message = SystemConstants.NO_CONTENT;
                break;
            case 400:
                message = SystemConstants.BAD_REQUEST;
                break;
            case 401:
                message = SystemConstants.UNAUTHORIZED;
                break;
            case 403:
                message = SystemConstants.FORBIDDEN;
                break;
            case 404:
                message = SystemConstants.NOT_FOUND;
                break;
            case 405:
                message = SystemConstants.METHOD_NOT_ALLOWED;
                break;
            case 406:
                message = SystemConstants.NOT_ACCEPTABLE_MASSAGE;
                break;
            case 409:
                message = SystemConstants.CONFLICT;
                break;
            case 415:
                message = SystemConstants.UNSUPPORTED_MEDIA;
                break;
            case 500:
                message = SystemConstants.INTERNAL_SERVER_ERROR;
                break;
            case 503:
                message = SystemConstants.SERVER_UNAVAILABLE;
                break;
            case 504:
                message = SystemConstants.GATEWAY_TIMEOUT;
                break;
            case 505:
                message = SystemConstants.HTTP_VERSION_NOT_SUPPORT;
                break;
            case 507:
                message = SystemConstants.INSUFFICIENT_STORAGE;
                break;
            default:
                message = SystemConstants.UNKNOWN_STATUS_CODE + statusCode;
                break;
        }

        String entity = "";
        if (statusCode == 200 || statusCode == 201 || statusCode == 202) {
        } else {
            if (!response.isCommitted()) {
                response.resetBuffer();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write(statusCode + " : " + message + "\n" + entity);
                response.flushBuffer();
            }
        }
    }
}

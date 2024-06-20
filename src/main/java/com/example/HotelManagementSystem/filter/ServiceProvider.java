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

        String ipAddress = request.getHeader(SystemConstants.IP_HEADER);
        String requestUri = request.getRequestURI();
        String authorization = request.getHeader(SystemConstants.TOKEN_NAME_ON_HEADER);

        boolean isAllowed = false;

        if ((requestUri.contains(SystemPaths.LOGIN_PATH) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.SIGNUP) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEPASSWORD) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEUSERNAME) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEROLE) && ipAddress != null)
        ) {
            isAllowed = true;
        } else if (check.CheckJWTIfForUser(authorization) && ipAddress != null) {
            String roleOfToken = null;
            if (authorization != null) {
                roleOfToken = check.WhatRole(authorization);
            }
            List<RollesAllowed> listOfRolls = new ArrayList<>();

            RollesAllowed Admin = new RollesAllowed(SystemConstants.ADMIN);
            Admin.getPaths().add("/");
            listOfRolls.add(Admin);

            RollesAllowed User = new RollesAllowed(SystemConstants.USER);
            User.getPaths().add("/");
            listOfRolls.add(User);

            for (RollesAllowed item : listOfRolls) {
                if (item.getRole().equalsIgnoreCase(roleOfToken)) {
                    for (String path : item.getPaths()) {
                        if (requestUri.contains(path)) {
                            isAllowed = true;
                            break;
                        }
                    }
                }
            }
            if (!isAllowed) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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
            System.out.println("Response is OK and fuck you");
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

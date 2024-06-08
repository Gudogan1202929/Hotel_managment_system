package com.example.HotelManagementSystem.filter;

import com.example.HotelManagementSystem.user.dto.Rolles;
import com.example.HotelManagementSystem.user.entity.LogFile;
import com.example.HotelManagementSystem.user.jwt.Check;
import com.example.HotelManagementSystem.user.service.LogFileService;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import com.example.HotelManagementSystem.utils.constant.SystemPaths;
import com.example.HotelManagementSystem.utils.encryption.Encryption;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceProvider implements ServiceProviderInterface {

    private final LogFileService logFileService;
    private final Check check;

    @Autowired
    public ServiceProvider(LogFileService logFileService, Check check) {
        this.logFileService = logFileService;
        this.check = check;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

        String ipAddress = containerRequestContext.getHeaderString(SystemConstants.IP_HEADER);
        String requestUri = containerRequestContext.getUriInfo().getRequestUri().toString();
        String authorization = containerRequestContext.getHeaderString(SystemConstants.TOKEN_NAME_ON_HEADER);

        System.out.println("allowed");


        boolean isAllowed = false;

        if ((requestUri.contains(SystemPaths.LOGIN_PATH) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.SIGNUP) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEPASSWORD) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEUSERNAME) && ipAddress != null) ||
                (requestUri.contains(SystemPaths.CHANGEROLE) && ipAddress != null)
        ) {
        } else if (check.CheckJWTIfForUser(authorization) && ipAddress != null) {
            String roleOfToken = null;
            if (authorization != null) {
                roleOfToken = check.WhatRole(authorization);
            }
            List<Rolles> listOfRolls = new ArrayList<>();

            Rolles Admin = new Rolles(SystemConstants.ADMIN);
            Admin.getPaths().add("/");
            listOfRolls.add(Admin);

            Rolles User = new Rolles(SystemConstants.USER);
            User.getPaths().add("/");
            listOfRolls.add(User);

            for (Rolles item : listOfRolls) {
                if (item.getRole().equalsIgnoreCase(roleOfToken)) {
                    for (String path : item.getPaths()) {
                        if (requestUri.contains(path)) {
                            isAllowed = true;
                            System.out.println("allowed");
                            System.out.println(isAllowed);
                            break;
                        }
                    }
                }
            }
            if (!isAllowed) {
                containerRequestContext.abortWith(Response.status(Response.Status.METHOD_NOT_ALLOWED).build());
            }
        } else {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        LogFile iPsModel = new LogFile(currentDateTime.toString(), ipAddress, requestUri);
        logFileService.saveLog(iPsModel);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        int statusCode = containerResponseContext.getStatus();
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
        if ((statusCode == 200 || statusCode == 201 || statusCode == 202)) {
        } else {
            try {
                entity = containerResponseContext.getEntity().toString();
                containerResponseContext.setEntity(statusCode + " : " + message + "\n" + entity);
            } catch (Exception e) {
                containerResponseContext.setEntity(statusCode + " : " + message + "\n" + entity);
            }
        }
    }
}

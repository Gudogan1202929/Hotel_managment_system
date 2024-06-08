package com.example.HotelManagementSystem.utils.constant;

public class SystemConstants {
    public static final String IP_HEADER = "X-Forwarded-For";
    public static final String TOKEN_NAME_ON_HEADER = "token";
    public static final int TOKEN_EXPIRATION_TIME = 1800000;
    public static final String ISSUER = "issuer";
    public static final String ROLE = "role";
    public static final String EXPIRED = "exp";
    public static final String TYPE_ENC = "AES";
    public static final String TYPE_HASHING = "SHA-256";
    public static final String THE_KEY_FOR_TOKEN = "RMxFVV95Y4Djnv7RouEsrs0eOHdmZ4st";
    public static final String THE_KEY_FOR_ENC = "GXBD91tMbO47qkExaDLuOVU6K4fEu0V8";
    public static final String CANNT_LOGIN ="cannot login something went wrong";
    public static final String CANNT_SIGNUP = "cannot Sign up something went wrong";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String OK = "OK: The request was successful.";
    public static final String CREATED = "Created: The request has been fulfilled, resulting in the creation of a new resource.";
    public static final String NO_CONTENT = "No Content: The server successfully processed the request, but there's no response body.";
    public static final String BAD_REQUEST = "Bad Request: The request could not be understood or was missing required parameters.";
    public static final String UNAUTHORIZED = "Unauthorized: Authentication failed or user doesn't have permissions.";
    public static final String FORBIDDEN = "Forbidden: Access denied.";
    public static final String NOT_FOUND = "Not Found: The requested resource was not found.";
    public static final String METHOD_NOT_ALLOWED = "Method Not Allowed: The request method is not supported for the requested resource.";
    public static final String NOT_ACCEPTABLE_MASSAGE = "Not Acceptable: The requested resource is not capable of generating content.";
    public static final String CONFLICT = "Conflict: The request could not be completed due to a conflict with the current state of the resource.";
    public static final String UNSUPPORTED_MEDIA = "Unsupported Media Type: The request entity has a media type which the server does not support.";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error: An unexpected condition was encountered.";
    public static final String SERVER_UNAVAILABLE = "Service Unavailable: The server is not ready to handle the request.";
    public static final String GATEWAY_TIMEOUT = "Gateway Timeout: The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server.";
    public static final String HTTP_VERSION_NOT_SUPPORT = "HTTP Version Not Supported: The server does not support the HTTP protocol version used in the request.";
    public static final String INSUFFICIENT_STORAGE = "Insufficient Storage: The method could not be performed on the resource because the server is unable to store the representation needed to successfully complete the request.";
    public static final String UNKNOWN_STATUS_CODE = "Unknown Status Code: ";
}

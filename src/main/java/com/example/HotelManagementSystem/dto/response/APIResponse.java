package com.example.HotelManagementSystem.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse<T>{

    private String status;

    private Integer httpStatus;

    private String message;

    private T data;


    /**
     * Creates an APIResponse for a successful operation.
     *
     * @param data             The data to include in the response.
     * @param message          The response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a successful operation.
     */
    public static <T> APIResponse<T> ok(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.OK.value())
                .status("success")
                .message(message)
                .data(data)
                .build();
    }


    /**
     * Creates an APIResponse for creating a new resource.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a successful operation.
     */
    public static <T> APIResponse<T> created(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.CREATED.value())
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an APIResponse for deleting a resource.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a successful operation.
     */
    public static <T> APIResponse<T> deleted(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.OK.value())
                .status("success")
                .message(message)
                .data(data)
                .build();
    }
    /**
     * Creates an APIResponse for a bad request operation.
     *
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> badRequest(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }


    /**
     * Creates an APIResponse for unauthorized access operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> unauthorized(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }


    /**
     * Creates an APIResponse for a forbidden access operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> forbidden(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.FORBIDDEN.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an APIResponse for a not found operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> notFound(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an APIResponse for a method not allowed operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> methodNotAllowed(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an APIResponse for unsupported media type operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> unsupportedMediaType(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an APIResponse for internal server error operation.
     * @param data             The data to include in the response.
     * @param message          the response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> internalServerError(T data, String message) {
        return APIResponse.<T>builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("error")
                .message(message)
                .data(data)
                .build();
    }

}

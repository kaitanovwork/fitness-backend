package kz.kaitanov.fitnessbackend.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

    protected Integer code;
    protected T data;
    protected boolean success;
    protected String error;

    public static <T> SuccessResponse<T> ok(Integer code, T data) {
        return new SuccessResponse<>(code, data);
    }

    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(200, data);
    }

    public static <T> ErrorResponse<T> errorResponse(Integer code, String error) {
        return new ErrorResponse<>(code, error);
    }

    public static <T> ErrorResponse<T> errorResponse(String error) {
        return new ErrorResponse<>(400, error);
    }
}
package kz.kaitanov.fitnessbackend.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@Setter
public class Response<T> {

    protected Integer code;
    protected Boolean success;

    protected Response(Integer code, Boolean success) {
        this.code = code;
        this.success = success;
    }

    public static <T> SuccessResponse<T> ok() {
        return new SuccessResponse<>(HttpStatus.OK.value(), Boolean.TRUE);
    }

    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(HttpStatus.OK.value(), Boolean.TRUE, data);
    }

    public static <T> SuccessResponse<T> ok(Integer code, T data) {
        return new SuccessResponse<>(code, Boolean.TRUE, data);
    }

    public static <T> ErrorResponse<T> error() {
        return new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE);
    }

    public static <T> ErrorResponse<T> error(String error) {
        return new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, error);
    }

    public static <T> ErrorResponse<T> error(Integer code, String error) {
        return new ErrorResponse<>(code, Boolean.FALSE, error);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return Objects.equals(code, response.code) && Objects.equals(success, response.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, success);
    }
}

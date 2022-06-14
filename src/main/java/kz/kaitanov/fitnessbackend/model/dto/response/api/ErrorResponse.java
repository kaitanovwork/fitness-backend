package kz.kaitanov.fitnessbackend.model.dto.response.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ErrorResponse<T> extends Response<T> {

    private String error;

    public ErrorResponse(Integer code, Boolean success) {
        super(code, success);
    }

    public ErrorResponse(Integer code, Boolean success, String error) {
        super(code, success);
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ErrorResponse<?> that = (ErrorResponse<?>) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), error);
    }
}

package kz.kaitanov.fitnessbackend.model.dto.response.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class SuccessResponse<T> extends Response<T> {

    private T data;

    public SuccessResponse(Integer code, Boolean success) {
        super(code, success);
    }

    public SuccessResponse(Integer code, Boolean success, T data) {
        super(code, success);
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SuccessResponse<?> that = (SuccessResponse<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}

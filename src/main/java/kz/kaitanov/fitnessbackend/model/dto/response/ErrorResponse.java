package kz.kaitanov.fitnessbackend.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse<T> extends Response<T> {

    public ErrorResponse(Integer code, String error) {
        super.code = code;
        super.error = error;
        super.success = false;
    }
}

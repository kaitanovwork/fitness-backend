package kz.kaitanov.fitnessbackend.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends Response<T> {

    public SuccessResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
        this.success = true;
    }
}
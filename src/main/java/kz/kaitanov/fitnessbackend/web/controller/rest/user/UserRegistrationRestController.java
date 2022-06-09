package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "UserRegistrationRestController", description = "Контроллер для регистрации нового пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class UserRegistrationRestController {

    private final UserService userService;

    @Operation(summary = "Эндпоинт для регистрации нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно зарегестрирован"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PostMapping
    public Response<UserResponseDto> registerNewUser(@RequestBody @Valid UserRegistrationRequestDto dto) {
        ApiValidationUtil.requireFalse(userService.existsByUsername(dto.username()), "username is being used by another user");
        ApiValidationUtil.requireFalse(dto.email() != null && userService.existsByEmail(dto.email()), "email is being used by another user");
        ApiValidationUtil.requireFalse(dto.phone() != null && userService.existsByPhone(dto.phone()), "phone is being used by another user");
        User newUser = userService.save(UserMapper.toEntity(dto));
        return Response.ok(UserMapper.toDto(newUser));
    }
}

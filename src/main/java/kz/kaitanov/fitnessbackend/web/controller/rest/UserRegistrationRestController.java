package kz.kaitanov.fitnessbackend.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.UserPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.implementations.model.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@Tag(name = "UserRegistrationRestController", description = "Проверка данных при регистрации пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class UserRegistrationRestController {

    private final UserServiceImpl userService;

    @Operation(summary = "Эндпоинт для регистрации пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация пользователя прошла успешно")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> registrationValidation(@RequestBody @Valid UserPersistRequestDto userPersistRequestDto) {
        if (userService.existsByUsername(userPersistRequestDto.username()) ||
                userService.existsByEmail(userPersistRequestDto.email()) ||
                userService.existsByPhone(userPersistRequestDto.phone())) {
            throw new UsernameNotFoundException("username, email or phone is being used by another user");
        }
        User user = userService.save(UserMapper.toEntity(userPersistRequestDto));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }
}

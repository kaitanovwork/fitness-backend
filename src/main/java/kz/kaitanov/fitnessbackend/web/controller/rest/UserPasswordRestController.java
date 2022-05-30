package kz.kaitanov.fitnessbackend.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "UserPasswordRestController", description = "Операции обновления пароля пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/password")
public class UserPasswordRestController {

    private final UserService userService;

    @Operation(summary = "Обновление пароля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль пользователя успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserPassword(@RequestBody @Valid UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        Optional<User> user = userService.findByIdWithRoles(userUpdatePasswordRequestDto.id());
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User userFromDto = userService.update(UserMapper.toEntity(user.get(), userUpdatePasswordRequestDto));
        return ResponseEntity.ok(UserMapper.toDto(userFromDto));
    }
}

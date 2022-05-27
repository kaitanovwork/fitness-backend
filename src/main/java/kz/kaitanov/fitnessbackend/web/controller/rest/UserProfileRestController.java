package kz.kaitanov.fitnessbackend.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.UserUpdateProfileDto;
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

@Tag(name = "UserProfileRestController", description = "Операции обновления профиля пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/profile")
public class UserProfileRestController {

    private final UserService userService;

    @Operation(summary = "Обновление профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль пользователя успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping
    public ResponseEntity<Void> updateUserProfile(@RequestBody @Valid UserUpdateProfileDto userUpdateProfileDto) {
        if (!userService.existsById(userUpdateProfileDto.id())) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> user = userService.findById(userUpdateProfileDto.id());
        UserMapper.toEntity(user.get(), userUpdateProfileDto);
        userService.update(UserMapper.toEntity(user.get(), userUpdateProfileDto));
        return ResponseEntity.ok().build();
    }
}

package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminUserRestController", description = "CRUD  операции над пользователями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserRestController {

    private final UserService userService;
    private final UserResponseDtoService userResponseDtoService;

    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userService.save(UserMapper.toEntity(userRegistrationRequestDto));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Обновление существующего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Существующий пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        if (!userService.existsById(userUpdateRequestDto.id())) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.update(UserMapper.toEntity(userUpdateRequestDto));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Получение списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех пользователей успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return ResponseEntity.ok(userResponseDtoService.findAll());
    }

    @Operation(summary = "Получение постраничного списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Постраничный список всех пользователей успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getPaginatedUserList() {
        Pageable pageable =

        return ResponseEntity.ok(userResponseDtoService.findAll());
    }

    @Operation(summary = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findById(userId);
        return userResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByUsername(username);
        return userResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByEmail(email);
        return userResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение пользователя по phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<UserResponseDto> getUserByPhone(@PathVariable String phone) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByPhone(phone);
        return userResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        if (!userService.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}

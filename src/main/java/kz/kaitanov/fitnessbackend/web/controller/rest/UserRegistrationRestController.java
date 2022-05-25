package kz.kaitanov.fitnessbackend.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.dto.request.UserPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "UserRegistrationRestController", description = "Проверка данных для регистрации")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class UserRegistrationRestController {

    private final UserResponseDtoService userResponseDtoService;

    @Operation(summary = "Эндпоинт для регистрации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация разрешена")
    })
    @PostMapping
    public ResponseEntity<Void> registrationValidation(@RequestBody @Valid UserPersistRequestDto userPersistRequestDto) {
        Optional<UserResponseDto> userFoundByUsername = userResponseDtoService.findByUsername(userPersistRequestDto.username());
        Optional<UserResponseDto> userFoundByEmail = userResponseDtoService.findByUsername(userPersistRequestDto.email());
        Optional<UserResponseDto> userFoundByPhone = userResponseDtoService.findByUsername(userPersistRequestDto.phone());
        if (userFoundByUsername.isPresent() || userFoundByEmail.isPresent() || userFoundByPhone.isPresent()) {
            throw new UsernameNotFoundException("username, email or phone is being used by another user");
        }
        return ResponseEntity.ok().build();
    }
}

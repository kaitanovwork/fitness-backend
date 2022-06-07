package kz.kaitanov.fitnessbackend.web.controller.rest.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import kz.kaitanov.fitnessbackend.model.dto.response.JwtResponse;
import kz.kaitanov.fitnessbackend.model.dto.response.Response;
import kz.kaitanov.fitnessbackend.web.config.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "JwtAuthenticationRestController", description = "Аутентификация пользователя в системе")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authenticate")
public class JwtAuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Получение токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно получен")
    })
    @PostMapping
    public Response<JwtResponse> createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        authenticate(jwtRequest.username(), jwtRequest.password());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.username());
        return Response.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

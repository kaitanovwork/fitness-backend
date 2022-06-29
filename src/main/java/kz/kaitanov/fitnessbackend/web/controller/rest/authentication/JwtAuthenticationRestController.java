package kz.kaitanov.fitnessbackend.web.controller.rest.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import kz.kaitanov.fitnessbackend.model.dto.response.JwtResponse;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.web.config.util.JwtTokenUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @Operation(summary = "Обновление токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлен")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'COACH')")
    @PostMapping("/refresh")
    public Response<JwtResponse> refreshToken(@RequestHeader("Authorization") String token) {
        final UserDetails userDetails;
        String jwtToken = null;
        String username = null;

        if (token != null && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (Exception ignored) {
            }
        }
        userDetails = userDetailsService.loadUserByUsername(username);

        if(!jwtTokenUtil.validateToken(jwtToken, userDetails)) {
            return Response.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
        } else {
            return Response.ok(new JwtResponse(jwtToken));
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

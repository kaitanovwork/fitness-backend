package kz.kaitanov.fitnessbackend.web.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(objectMapper
                .writeValueAsString(Response
                        .error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage())));
    }
}

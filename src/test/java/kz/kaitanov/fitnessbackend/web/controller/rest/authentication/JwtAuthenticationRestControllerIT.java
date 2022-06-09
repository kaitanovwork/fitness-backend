package kz.kaitanov.fitnessbackend.web.controller.rest.authentication;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthenticationRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_SuccessfulTest/AfterTest.sql")
    public void createAuthenticationToken_SuccessfulTest() throws Exception {
        getToken("username", "pass");
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_UsernameNotFoundTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_UsernameNotFoundTest/AfterTest.sql")
    public void createAuthenticationToken_UsernameNotFoundTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("user1", "pass");

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Логин и/или пароль указаны неверно")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_PasswordNotFoundTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/createAuthenticationToken_PasswordNotFoundTest/AfterTest.sql")
    public void createAuthenticationToken_PasswordNotFoundTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("username", "password");

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Логин и/или пароль указаны неверно")));
    }
}

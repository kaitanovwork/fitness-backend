package kz.kaitanov.fitnessbackend.web.controller.rest.authentication;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import org.hamcrest.core.Is;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthenticationRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_SuccessfulTest/AfterTest.sql")
    public void сreateAuthenticationToken_SuccessfulTest() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("username", "pass");

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jwtToken").value(StringContains.containsString("ey")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_UsernameNotFoundTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_UsernameNotFoundTest/AfterTest.sql")
    public void сreateAuthenticationToken_UsernameNotFoundTest() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("user1", "pass");

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_PasswordNotFoundTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/authentication/JwtAuthenticationRestController/сreateAuthenticationToken_PasswordNotFoundTest/AfterTest.sql")
    public void сreateAuthenticationToken_PasswordNotFoundTest() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("username", "password");

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }
}

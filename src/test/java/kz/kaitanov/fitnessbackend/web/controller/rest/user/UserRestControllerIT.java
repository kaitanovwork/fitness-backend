package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateEmailRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePhoneRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.Gender;
import kz.kaitanov.fitnessbackend.web.controller.rest.authentication.JwtAuthenticationRestController;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UserRestControllerIT extends SpringSimpleContextTest {

    @Autowired
    private JwtAuthenticationRestController jwtAuthenticationRestController;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/updateUserProfile_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/updateUserProfile_SuccessfulTest/AfterTest.sql")
    public void updateUserProfile_SuccessfulTest() throws Exception {
        JwtRequest jwt = new JwtRequest("user101", "pass");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        UserUpdateProfileRequestDto dto = new UserUpdateProfileRequestDto("firstName", "lastName", 20, Gender.MALE);
        mockMvc.perform(put("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", Is.is(dto.firstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName", Is.is(dto.lastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.age", Is.is(dto.age())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender", Is.is(dto.gender().toString())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM User u
                                WHERE u.firstName = :firstName AND u.lastName = :lastName AND u.age = :age AND u.gender = :gender
                                """, Boolean.class)
                .setParameter("firstName", dto.firstName())
                .setParameter("lastName", dto.lastName())
                .setParameter("age", dto.age())
                .setParameter("gender", dto.gender())
                .getSingleResult());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/updateUserPassword_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/updateUserPassword_SuccessfulTest/AfterTest.sql")
    public void updateUserPassword_SuccessfulTest() throws Exception {
        JwtRequest jwt = new JwtRequest("user101", "pass");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        UserUpdatePasswordRequestDto dto = new UserUpdatePasswordRequestDto("1");
        mockMvc.perform(put("/api/v1/user/password")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));
        /*      .andExpect(MockMvcResultMatchers.jsonPath("$.data.password", Is.is(dto.password())));*/

        assertThat(entityManager.createQuery(
                        """
                                SELECT COUNT (u.password)
                                FROM User u
                                WHERE u.password = :password
                                """, Integer.class)
                .setParameter("password", "$2a$12$F6hG14kJmuGz4KBgrp4xL.pvlcf8FvviTRqEe4i0ze2.9VvmamEnW")
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/updateUserPassword_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/updateUserPassword_SuccessfulTest/AfterTest.sql")
    public void updateUserPassword_WithExistedPassword() throws Exception {
        JwtRequest jwt = new JwtRequest("user101", "pass");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        UserUpdatePasswordRequestDto dto = new UserUpdatePasswordRequestDto("Artemiy");
        mockMvc.perform(put("/api/v1/user/password")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password", Is.is(dto.password())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM User u
                                WHERE u.password = :password 
                                """, Boolean.class)
                .setParameter("password", dto.password())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/updateUserEmail_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/updateUserEmail_SuccessfulTest/AfterTest.sql")
    public void updateUserEmail_SuccessfulTest() throws Exception {
        JwtRequest jwt = new JwtRequest("user101", "pass");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        UserUpdateEmailRequestDto dto = new UserUpdateEmailRequestDto("user101@newEmail.ru");
        mockMvc.perform(put("/api/v1/user/email")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", Is.is(dto.email())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.email) > 0
                                FROM User u
                                WHERE u.email = :email
                                """, Boolean.class)
                .setParameter("email", dto.email())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/updateUserPhone_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/updateUserPhone_SuccessfulTest/AfterTest.sql")
    public void updateUserPhone_SuccessfulTest() throws Exception {
        JwtRequest jwt = new JwtRequest("user101", "pass");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        UserUpdatePhoneRequestDto dto = new UserUpdatePhoneRequestDto("89050001122");
        mockMvc.perform(put("/api/v1/user/phone")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phone", Is.is(dto.phone())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.phone) > 0
                                FROM User u
                                WHERE u.phone = :phone
                                """, Boolean.class)
                .setParameter("phone", dto.phone())
                .getSingleResult());
    }

}
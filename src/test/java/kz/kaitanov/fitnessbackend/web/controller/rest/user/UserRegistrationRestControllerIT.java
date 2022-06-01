package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.Gender;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRegistrationRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRegistrationRestController/registerNewUser_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRegistrationRestController/registerNewUser_SuccessfulTest/AfterTest.sql")
    public void registerNewUser_SuccessfulTest() throws Exception {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto("username", "password", "firstName", "lastName", "test@gmail.com", "77028883322", 20, Gender.MALE);

        mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username", Is.is(dto.username())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", Is.is(dto.firstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName", Is.is(dto.lastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", Is.is(dto.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phone", Is.is(dto.phone())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.age", Is.is(dto.age())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender", Is.is(dto.gender().name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM User u
                                WHERE u.username = :username AND u.firstName = :firstName AND u.lastName = :lastName AND u.email = :email
                                AND u.phone = :phone AND u.age = :age AND u.gender = :gender
                                  """,
                        Boolean.class)
                .setParameter("username", dto.username())
                .setParameter("firstName", dto.firstName())
                .setParameter("lastName", dto.lastName())
                .setParameter("email", dto.email())
                .setParameter("phone", dto.phone())
                .setParameter("age", dto.age())
                .setParameter("gender", dto.gender())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRegistrationRestController/registerNewUser_WithExistingUsernameTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRegistrationRestController/registerNewUser_WithExistingUsernameTest/AfterTest.sql")
    public void registerNewUser_WithExistingUsernameTest() throws Exception {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto("username", "pass", "first", "last", "te@gmail.com", "77028883", 26, Gender.MALE);

        mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("username is being used by another user")));
    }
}

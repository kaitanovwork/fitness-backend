package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateEmailRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePhoneRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.Gender;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

//TODO проверить все тесты и исправить если нужно
public class UserRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserProfile_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserProfile_SuccessfulTest/AfterTest.sql")
    public void updateUserProfile_SuccessfulTest() throws Exception {

        String token = getToken("user101", "pass");
        UserUpdateProfileRequestDto dto = new UserUpdateProfileRequestDto("firstName", "lastName", 20, Gender.MALE);
        mockMvc.perform(put("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPassword_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPassword_SuccessfulTest/AfterTest.sql")
    public void updateUserPassword_SuccessfulTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdatePasswordRequestDto dto = new UserUpdatePasswordRequestDto("Artemiy");
        String oldPassword = entityManager.createQuery(
                        """
                                SELECT u.password
                                FROM User u
                                WHERE u.id = :id
                                """, String.class)
                .setParameter("id", 101L)
                .getSingleResult();
        mockMvc.perform(put("/api/v1/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));
        String newPassword = entityManager.createQuery(
                        """
                                SELECT u.password
                                FROM User u
                                WHERE u.id = :id
                                """, String.class)
                .setParameter("id", 101L)
                .getSingleResult();
        assertNotSame(newPassword, oldPassword);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPassword_WithEmptyPasswordValueTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPassword_WithEmptyPasswordValueTest/AfterTest.sql")
    public void updateUserPassword_WithEmptyPasswordValueTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdatePasswordRequestDto dto = new UserUpdatePasswordRequestDto("");
        mockMvc.perform(put("/api/v1/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserEmail_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserEmail_SuccessfulTest/AfterTest.sql")
    public void updateUserEmail_SuccessfulTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdateEmailRequestDto dto = new UserUpdateEmailRequestDto("user101@newEmail.ru");
        mockMvc.perform(put("/api/v1/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserEmail_WithExistedEmailTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserEmail_WithExistedEmailTest/AfterTest.sql")
    public void updateUserEmail_WithExistedEmailTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdateEmailRequestDto dto = new UserUpdateEmailRequestDto("user101test@gmail.com");
        mockMvc.perform(put("/api/v1/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("email is being used by another user")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPhone_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPhone_SuccessfulTest/AfterTest.sql")
    public void updateUserPhone_SuccessfulTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdatePhoneRequestDto dto = new UserUpdatePhoneRequestDto("89050001122");
        mockMvc.perform(put("/api/v1/user/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
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

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPhone_WithExistedPhoneTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/user/UserRestController/updateUserPhone_WithExistedPhoneTest/AfterTest.sql")
    public void updateUserPhone_WithExistedPhoneTest() throws Exception {
        String token = getToken("user101", "pass");
        UserUpdatePhoneRequestDto dto = new UserUpdatePhoneRequestDto("89999999101");
        mockMvc.perform(put("/api/v1/user/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("phone is being used by another user")));
    }
}
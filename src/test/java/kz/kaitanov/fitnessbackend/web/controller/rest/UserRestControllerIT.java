package kz.kaitanov.fitnessbackend.web.controller.rest;


import kz.kaitanov.fitnessbackend.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
@RequiredArgsConstructor
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/UserRestController/create-user-before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/UserRestController/clear-user-after.sql")
public class UserRestControllerIT {

    private final MockMvc mockMvc;
    private final UserRestController userRestController;

    @Test
    void shouldGetUserList() throws Exception {
        mockMvc.perform(get("/api/v1/user")).
                andExpect(status().isOk())
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/v1/user")).
                andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        mockMvc.perform(put("/api/v1/user")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/v1/user/101")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetUserByUsername() throws Exception {
        mockMvc.perform(get("/api/v1/user/username/user1test")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetUserByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/user/email/user1test@gmail.com")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetUserByPhone() throws Exception {
        mockMvc.perform(get("/api/v1/user/phone/89999999101")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/user/104")).
                andExpect(status().isNotFound())
                .andExpect(status().isOk())
                .andDo(print());
    }
}



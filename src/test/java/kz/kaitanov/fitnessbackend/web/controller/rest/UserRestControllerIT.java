package kz.kaitanov.fitnessbackend.web.controller.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        value = "/scripts/UserRestController/create-user-before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        value = "/scripts/UserRestController/clear-user-after.sql")
public class UserRestControllerIT extends SpringSimpleContextTest {


    @Autowired
    private AdminUserRestController adminUserRestController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test  // �������� ��������� ��������� �� ����
    public void givenWac_whenServletContext_thenItProvidesUserRestController() {
        final ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("adminUserRestController"));
    }

//    public static RequestPostProcessor authentication() {
//        return request -> {
//            request.addHeader("Authorization", getBasicAuthHeader("test101user", "1"));
//            return request;
//        };
//    }
//        public static String getBasicAuthHeader(String username, String password) {
//            return "secret_key_abrakadabra";
//        }

    public static String objectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test
    void shouldGetUserList() throws Exception {
        this.mockMvc.perform(get("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldSaveUser() throws Exception {
        User user = new User(105l, "user105", "1", "user105test", "user105test",
                "user105test@gmail.com", "89999999105", 20, null, null);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectToJson(user)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user105"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("user105test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("user105test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("89999999105"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user105test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("20"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());

    }

    @Test
    void shouldUpdateUser() throws Exception {
        User userUpd = new User(104l, "user144", "1", "user104test", "user104test",
                "user444test@gmail.com", "104", 104, null, null);
        MvcResult mvcResult = this.mockMvc.perform(put("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectToJson(userUpd)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user144"))
                .andExpect(status().isOk())

                .andDo(print())
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());

    }

    @Test
    void shouldGetUserById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/user/{userId}", "101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("101"))
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser("user101")
    void shouldGetUserByUsername() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/user/username/{username}", "user101")).
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user101"))
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }


    @Test
    void shouldGetUserByEmail() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/user/email/{email}", "user101test@gmail.com"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user101test@gmail.com"))
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    void shouldGetUserByPhone() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/user/phone/{phone}", "89999999101")).
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("89999999101"))
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }


    @Test
    void shouldDeleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/user/{userId}", "104")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}



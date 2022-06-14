package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRoleRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleList_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleList_SuccessfulTest/AfterTest.sql")
    public void getRoleList_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/role")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].id", hasItems(101, 102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].name", hasItems("ADMIN", "USER")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleByName_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleByName_SuccessfulTest/AfterTest.sql")
    void getRoleByName_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/role/name/{name}", "ADMIN")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(RoleName.ADMIN.name())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_SuccessfulTest/AfterTest.sql")
    void getRoleById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/role/{roleId}", 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(RoleName.ADMIN.name())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_IdNotFoundTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_IdNotFoundTest/AfterTest.sql")
    void getRoleById_IdNotFoundTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/role/{roleId}", 102)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Role by id 102 not found")));
    }
}

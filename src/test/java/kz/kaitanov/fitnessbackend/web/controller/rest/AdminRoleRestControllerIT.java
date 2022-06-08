package kz.kaitanov.fitnessbackend.web.controller.rest;


import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import kz.kaitanov.fitnessbackend.model.dto.response.Response;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import kz.kaitanov.fitnessbackend.web.controller.rest.admin.AdminRoleRestController;
import kz.kaitanov.fitnessbackend.web.controller.rest.authentication.JwtAuthenticationRestController;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
public class AdminRoleRestControllerIT extends SpringSimpleContextTest {

    @Autowired
    RoleService roleService;

    @Autowired
    AdminRoleRestController adminRoleRestController;

    @Autowired
    JwtAuthenticationRestController jwtAuthenticationRestController;


    @Autowired
    UserService userService;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleList_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleList_SuccessfulTest/AfterTest.sql")
    void getRoleList_SuccessfulTest() throws Exception {
        JwtRequest jwt  = new JwtRequest("username", "password");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        mockMvc.perform(get("/api/v1/admin/role").
                        accept(MediaType.APPLICATION_JSON).
                        header("Authorization", "Bearer " + token)).
                andExpect(status().isOk()).
                andExpect(content().json(objectMapper.writeValueAsString(Response.ok(roleService.findAll()))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,value ="/scripts/admin/AdminRoleRestController/getRoleByName_ShouldGetRoleByName/BeforeTest.sql" )
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleByName_ShouldGetRoleByName/AfterTest.sql")
    void getRoleByName_ShouldGetRoleByName() throws Exception {
        JwtRequest jwt  = new JwtRequest("username", "password");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        mockMvc.perform(get("/api/v1/admin/role/name/{name}", "ADMIN").
                        accept(MediaType.APPLICATION_JSON).
                        header("Authorization", "Bearer " + token)).
                andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true))).
                andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200))).
                andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(RoleName.ADMIN.toString())));


    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,value = "/scripts/admin/AdminRoleRestController/getRoleById_ShouldGetRoleById/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_ShouldGetRoleById/BeforeTest.sql")
    void getRoleById_ShouldGetRoleById() throws Exception {
        JwtRequest jwt  = new JwtRequest("username", "password");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        assertThat(adminRoleRestController).isNotNull();
        mockMvc.perform(get("/api/v1/admin/role/{roleId}", "101").
                        accept(MediaType.APPLICATION_JSON).
                        header("Authorization", "Bearer " + token)).
                andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200))).
                andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true))).
                andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(RoleName.ADMIN.toString())));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,value = "/scripts/admin/AdminRoleRestController/getRoleById_WithNonExistentId/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRoleRestController/getRoleById_WithNonExistentId/AfterTest.sql")
    void getRoleById_WithNonExistentId() throws Exception {
        JwtRequest jwt  = new JwtRequest("username", "password");
        String token = Objects.requireNonNull(jwtAuthenticationRestController.createAuthenticationToken(jwt).getBody()).jwtToken();
        assertThat(adminRoleRestController).isNotNull();
        mockMvc.perform(get("/api/v1/admin/role/{roleId}", "103").
                        accept(MediaType.APPLICATION_JSON).
                        header("Authorization", "Bearer " + token)).
                andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false))).
                andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400))).
                andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Role by id 103 not found")));
    }
}

package kz.kaitanov.fitnessbackend.web.controller.rest;

import kz.kaitanov.fitnessbackend.annotation.IT;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
@RequiredArgsConstructor
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/RoleRestController/create-role-before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/RoleRestController/clear-role-after.sql")
public class RoleRestControllerIT {

    private final MockMvc mockMvc;
    private final RoleRestController roleRestController;
    private RoleService roleService;


    @Test
    void shouldGetAllRoleList() throws Exception {
//        List<Role> roleList = new ArrayList<>();

        mockMvc.perform(get("/api/v1/role")).
                andExpect(status().isOk()).
                andExpect(status().isForbidden()).
                andDo(print());
    }

    @Test
    void shouldGetRoleByName() throws Exception {
        mockMvc.perform(get("/api/v1/role/name/{name}","admin")).
                andExpect(status().isOk()).
                andExpect(status().isNotFound()).
                andExpect(status().isForbidden()).
                andDo(print());
    }

    @Test
    void shouldGetRoleById() throws Exception {
        mockMvc.perform(get("/api/v1/role/{roleId}","1")).
                andExpect(status().isOk()).
                andExpect(status().isNotFound()).
                andExpect(status().isForbidden()).
                andDo(print());
    }

}

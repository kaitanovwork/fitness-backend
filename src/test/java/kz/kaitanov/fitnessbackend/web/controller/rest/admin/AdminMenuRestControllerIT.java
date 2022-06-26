package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminMenuRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/saveMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/saveMenu_SuccessfulTest/AfterTest.sql")
    public void saveMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        MenuPersistRequestDto dto = new MenuPersistRequestDto(ProgramType.WEIGHT_GAIN);

        mockMvc.perform(post("/api/v1/admin/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(dto.programType().toString())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(m.id) > 0
                                FROM Menu m
                                WHERE m.programType = :programType
                                """,
                        Boolean.class)
                .setParameter("programType", dto.programType())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/updateMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/updateMenu_SuccessfulTest/AfterTest.sql")
    public void updateMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        MenuUpdateRequestDto dto = new MenuUpdateRequestDto(101L, ProgramType.WEIGHT_GAIN);

        mockMvc.perform(put("/api/v1/admin/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(dto.id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(dto.programType().toString())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(m.id) > 0
                                FROM Menu m
                                WHERE m.id = :id AND m.programType = :programType
                                """,
                        Boolean.class)
                .setParameter("id", dto.id())
                .setParameter("programType", dto.programType())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/updateMenu_MenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/updateMenu_MenuNotFound/AfterTest.sql")
    public void updateMenu_MenuNotFound() throws Exception {

        String token = getToken("username", "password");
        MenuUpdateRequestDto dto = new MenuUpdateRequestDto(102L, ProgramType.WEIGHT_GAIN);

        mockMvc.perform(put("/api/v1/admin/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Menu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_SuccessfulTest/AfterTest.sql")
    public void addSubMenuToMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 101, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.subMenus[*].id", hasItems(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.subMenus[*].programType", hasItems("WEIGHT_GAIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.subMenus[*].weekDay", hasItems("WEDNESDAY")));


        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(m.id) > 0
                                FROM Menu m
                                WHERE m.id = :id AND m.subMenus.size = :subMenus
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("subMenus", 1)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_MenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_MenuNotFound/AfterTest.sql")
    public void addSubMenuToMenu_MenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 102, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Menu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/addSubMenuToMenu_SubMenuNotFound/AfterTest.sql")
    public void addSubMenuToMenu_SubMenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 101, 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_SuccessfulTest/AfterTest.sql")
    public void deleteSubMenuFromMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 101, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(m.id) > 0
                                FROM Menu m
                                WHERE m.id = :id AND m.subMenus.size = :subMenus
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("subMenus", 0)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_MenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_MenuNotFound/AfterTest.sql")
    public void deleteSubMenuFromMenu_MenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 102, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Menu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteSubMenuFromMenu_SubMenuNotFound/AfterTest.sql")
    public void deleteSubMenuFromMenu_SubMenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/menu/{menuId}/subMenu/{subMenuId}", 101, 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteMenuById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteMenuById_SuccessfulTest/AfterTest.sql")
    public void deleteMenuById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/menu/{menuId}", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));


        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(m.id) > 0
                                FROM Menu m
                                WHERE m.id = :id
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteMenuById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/deleteMenuById_SuccessfulTest/AfterTest.sql")
    public void deleteMenuById_MenuNotFound() throws Exception {
        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/menu/{menuId}", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Menu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuById_SuccessfulTest/AfterTest.sql")
    public void getMenuById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        Menu menu = new Menu();
        menu.setId(101L);
        menu.setProgramType(ProgramType.WEIGHT_GAIN);

        mockMvc.perform(get("/api/v1/admin/menu/{menuId}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(menu.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(menu.getProgramType().toString())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuById_MenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuById_MenuNotFound/AfterTest.sql")
    public void getMenuById_MenuNotFound() throws Exception {
        String token = getToken("username", "password");

        mockMvc.perform(get("/api/v1/admin/menu/{menuId}", 102)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Menu by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuPage_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminMenuRestController/getMenuPage_SuccessfulTest/AfterTest.sql")
    public void getMenuPage_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");

        mockMvc.perform(get("/api/v1/admin/menu")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].id", hasItems(101, 102, 103, 104, 105, 106, 107, 108, 109, 110)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements", Is.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].programType", hasItems(ProgramType.WEIGHT_GAIN.toString(), ProgramType.WEIGHT_LOSS.toString())));
    }
}

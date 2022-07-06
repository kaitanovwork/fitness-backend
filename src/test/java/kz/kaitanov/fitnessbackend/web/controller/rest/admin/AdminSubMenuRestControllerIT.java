package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import kz.kaitanov.fitnessbackend.model.enums.WeekDay;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO проверить все тесты и исправить если нужно
public class AdminSubMenuRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/saveSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/saveSubMenu_SuccessfulTest/AfterTest.sql")
    public void saveSubMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        SubMenuPersistRequestDto dto = new SubMenuPersistRequestDto(ProgramType.WEIGHT_GAIN, WeekDay.MONDAY);

        mockMvc.perform(post("/api/v1/admin/sub-menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(dto.programType().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.weekDay", Is.is(dto.weekDay().name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.programType = :programType AND s.weekDay = :weekDay
                                """,
                        Boolean.class)
                .setParameter("programType", dto.programType())
                .setParameter("weekDay", dto.weekDay())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/updateSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/updateSubMenu_SuccessfulTest/AfterTest.sql")
    public void updateSubMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        SubMenuUpdateRequestDto dto = new SubMenuUpdateRequestDto(101L, ProgramType.WEIGHT_LOSS, WeekDay.SATURDAY);

        mockMvc.perform(put("/api/v1/admin/sub-menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(dto.programType().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.weekDay", Is.is(dto.weekDay().toString())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.id = :id AND s.programType = :programType AND s.weekDay = :weekDay
                                """,
                        Boolean.class)
                .setParameter("id", dto.id())
                .setParameter("programType", dto.programType())
                .setParameter("weekDay", dto.weekDay())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/updateSubMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/updateSubMenu_SubMenuNotFound/AfterTest.sql")
    public void updateSubMenu_SubMenuNotFound() throws Exception {

        String token = getToken("username", "password");
        SubMenuUpdateRequestDto dto = new SubMenuUpdateRequestDto(102L, ProgramType.WEIGHT_LOSS, WeekDay.SATURDAY);

        mockMvc.perform(put("/api/v1/admin/sub-menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_SuccessfulTest/AfterTest.sql")
    public void addRecipeToSubMenu_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 101, 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].id", hasItems(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].calorie", hasItems(1500)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].carbohydrate", hasItems(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].description", hasItems("With chicken")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].fat", hasItems(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].name", hasItems("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].protein", hasItems(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.id = :id AND s.recipes.size = :recipes
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("recipes", 1)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_SuccessfulTest/AfterTest.sql")
    public void deleteRecipeFromSubMenu_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 101, 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.id = :id AND s.recipes.size = :recipes
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("recipes", 0)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuById_SuccessfulTest/AfterTest.sql")
    public void getRecipeById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/sub-menu/{recipeId}", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.programType", Is.is(ProgramType.WEIGHT_GAIN.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.weekDay", Is.is(WeekDay.MONDAY.name())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuPage_successfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuPage_successfulTest/AfterTest.sql")
    public void getSubMenuPage_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/sub-menu")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].id", hasItems(101, 102, 103, 104, 105, 106, 107, 108, 109, 110)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].programType", hasItems("WEIGHT_GAIN", "WEIGHT_GAIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].weekDay", hasItems("MONDAY", "TUESDAY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements", Is.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(11)));

        mockMvc.perform(get("/api/v1/admin/sub-menu?size=5&page=1&sort=id,desc")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].id", hasItems(106, 105, 104, 103, 102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].programType", hasItems("WEIGHT_GAIN", "WEIGHT_GAIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].weekDay", hasItems("MONDAY", "TUESDAY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(11)));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteSubMenuById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteSubMenuById_SuccessfulTest/AfterTest.sql")
    public void deleteSubMenuById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/sub-menu/{subMenuId}", 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertFalse(entityManager.createQuery(
                        """
                                     SELECT COUNT(s.id) > 0
                                     FROM SubMenu s
                                     WHERE s.id = :id
                                """, Boolean.class)
                .setParameter("id", 101L)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_SubMenuNotFound/AfterTest.sql")
    public void addRecipeToSubMenu_SubMenuNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 102, 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipeToSubMenu_RecipeNotFound/AfterTest.sql")
    public void addRecipeToSubMenu_RecipeNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 101, 102)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_SubMenuNotFound/AfterTest.sql")
    public void deleteRecipeFromSubMenu_SubMenuNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 102, 101)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipeFromSubMenu_RecipeNotFound/AfterTest.sql")
    public void deleteRecipeFromSubMenu_RecipeNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/sub-menu/{subMenuId}/recipe/{recipeId}", 101, 102)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuById_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/getSubMenuById_SubMenuNotFound/AfterTest.sql")
    public void getRecipeById_SubMenuNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/sub-menu/{recipeId}", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteSubMenuById_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteSubMenuById_SubMenuNotFound/AfterTest.sql")
    public void deleteRecipeById_SubMenuNotFound() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/sub-menu/{recipeId}", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipesToSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipesToSubMenu_SuccessfulTest/AfterTest.sql")
    public void addRecipesToSubMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/sub-menu/{subMenuId}/recipe?recipesId=101&recipesId=102", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].id", hasItems(101, 102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].calorie", hasItems(1700, 1600)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].carbohydrate", hasItems(180, 170)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].description", hasItems("With beet", "With crab")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].fat", hasItems(200, 100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].name", hasItems("PP", "Crab salad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[*].protein", hasItems(400, 300)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.id = :id AND s.recipes.size = :recipes
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("recipes", 2)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipesToSubMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/addRecipesToSubMenu_SubMenuNotFound/AfterTest.sql")
    public void addRecipesToSubMenu_SubMenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/sub-menu/{subMenuId}/recipe?recipesId=101&recipesId=102", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipesFromSubMenu_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipesFromSubMenu_SuccessfulTest/AfterTest.sql")
    public void deleteRecipesFromSubMenu_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/sub-menu/{subMenuId}/recipe?recipesId=101&recipesId=102", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(s.id) > 0
                                FROM SubMenu s
                                WHERE s.id = :id AND s.recipes.size = :recipes
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("recipes", 0)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipesFromSubMenu_SubMenuNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminSubMenuRestController/deleteRecipesFromSubMenu_SubMenuNotFound/AfterTest.sql")
    public void deleteRecipesFromSubMenu_SubMenuNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/sub-menu/{subMenuId}/recipe?recipesId=101&recipesId=102", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("SubMenu with id 102 not found")));
    }
}

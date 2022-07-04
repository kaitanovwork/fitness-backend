package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateRequestDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRecipeRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/saveRecipe_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/saveRecipe_SuccessfulTest/AfterTest.sql")
    public void saveRecipe_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        RecipePersistRequestDto dto = new RecipePersistRequestDto("name", "description", "picUrl");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(dto.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", Is.is(dto.description())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.name = :name AND r.description = :description AND r.picUrl = :picUrl
                                """,
                        Boolean.class)
                .setParameter("name", dto.name())
                .setParameter("description", dto.description())
                .setParameter("picUrl", dto.picUrl())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/saveRecipe_NameRecipeIsUsed/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/saveRecipe_NameRecipeIsUsed/AfterTest.sql")
    public void saveRecipe_NameRecipeIsUsed() throws Exception {

        String token = getToken("username", "password");
        RecipePersistRequestDto dto = new RecipePersistRequestDto("name", "description", "picUrl");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("name is being used by another recipe")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipe_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipe_SuccessfulTest/AfterTest.sql")
    public void updateRecipe_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        RecipeUpdateRequestDto dto = new RecipeUpdateRequestDto(101L, "description", "picUrl");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", Is.is(dto.description())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.id = :id AND r.description = :description AND r.picUrl = :picUrl
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("description", dto.description())
                .setParameter("picUrl", dto.picUrl())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipe_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipe_RecipeNotFound/AfterTest.sql")
    public void updateRecipe_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");
        RecipeUpdateRequestDto dto = new RecipeUpdateRequestDto(102L, "description", "picUrl");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_SuccessfulTest/AfterTest.sql")
    public void updateRecipeName_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");
        RecipeUpdateNameRequestDto dto = new RecipeUpdateNameRequestDto(101L, "name");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(dto.name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.id = :id AND r.name = :name
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("name", dto.name())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_RecipeNotFound/AfterTest.sql")
    public void updateRecipeName_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");
        RecipeUpdateNameRequestDto dto = new RecipeUpdateNameRequestDto(102L, "name");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_NameRecipeIsUsed/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/updateRecipeName_NameRecipeIsUsed/AfterTest.sql")
    public void updateRecipeName_NameRecipeIsUsed() throws Exception {

        String token = getToken("username", "password");
        RecipeUpdateNameRequestDto dto = new RecipeUpdateNameRequestDto(101L, "name");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("name is being used by another recipe")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_SuccessfulTest/AfterTest.sql")
    public void addProductToRecipe_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/{recipeId}/product/{productId}", 101, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].id", hasItems(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].calorie", hasItems(500)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].carbohydrate", hasItems(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].fat", hasItems(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].name", hasItems("Chicken")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[*].protein", hasItems(50)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.id = :id AND r.products.size = :products
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("products", 1)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_RecipeNotFound/AfterTest.sql")
    public void addProductToRecipe_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/{recipeId}/product/{productId}", 102, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_ProductNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/addProductToRecipe_ProductNotFound/AfterTest.sql")
    public void addProductToRecipe_ProductNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/recipe/{recipeId}/product/{productId}", 101, 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_SuccessfulTest/AfterTest.sql")
    public void deleteProductFromRecipe_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/recipe/{recipeId}/product/{productId}", 101, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.id = :id AND r.products.size = :products
                                """,
                        Boolean.class)
                .setParameter("id", 101L)
                .setParameter("products", 0)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_RecipeNotFound/AfterTest.sql")
    public void deleteProductFromRecipe_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/recipe/{recipeId}/product/{productId}", 102, 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_ProductNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteProductFromRecipe_ProductNotFound/AfterTest.sql")
    public void deleteProductFromRecipe_ProductNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/recipe/{recipeId}/product/{productId}", 101, 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipePage_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipePage_SuccessfulTest/AfterTest.sql")
    public void getRecipePage_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/recipe?size=4&page=2&sort=fat")
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].id", hasItems(110, 108, 111)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].calorie", hasItems(1500, 1000, 1400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].carbohydrate", hasItems(110, 130)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].description", hasItems("With spinach")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].fat", hasItems(330, 350, 370)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].name", hasItems("Salad with corn", "Tuna salad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].protein", hasItems(440, 450, 470)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipeById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipeById_SuccessfulTest/AfterTest.sql")
    public void getRecipeById_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/recipe/{recipeId}", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.calorie", Is.is(1500)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.carbohydrate", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", Is.is("With chicken")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fat", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is("Caesar salad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.protein", Is.is(200)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipeById_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/getRecipeById_RecipeNotFound/AfterTest.sql")
    public void getRecipeById_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/recipe/{recipeId}", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteRecipeById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteRecipeById_SuccessfulTest/AfterTest.sql")
    public void deleteRecipeById_SuccessfulTest() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/recipe/{recipeId}", 101)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(r.id) > 0
                                FROM Recipe r
                                WHERE r.id = :id""",
                        Boolean.class)
                .setParameter("id", 101L)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteRecipeById_RecipeNotFound/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminRecipeRestController/deleteRecipeById_RecipeNotFound/AfterTest.sql")
    public void deleteRecipeById_RecipeNotFound() throws Exception {

        String token = getToken("username", "password");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/recipe/{recipeId}", 102)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Recipe by id 102 not found")));
    }
}

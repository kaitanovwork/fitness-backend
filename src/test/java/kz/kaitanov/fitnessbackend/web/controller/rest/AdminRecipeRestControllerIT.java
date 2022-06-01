package kz.kaitanov.fitnessbackend.web.controller.rest;


import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/RecipeRestController/create-recipe-before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/RecipeRestController/clear-recipe-after.sql")
public class AdminRecipeRestControllerIT extends SpringSimpleContextTest {

    @Autowired
    private final MockMvc mockMvc;
    @Autowired
    private final AdminRecipeRestController controller;

    @Test
    void shouldGetRecipeList() throws Exception {
        mockMvc.perform(get("/api/v1/recipe")).
                andExpect(status().isOk())
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void shouldSaveRecipe() throws Exception {
        mockMvc.perform(post("/api/v1/recipe")).
                andExpect(status().isOk())
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
        mockMvc.perform(put("/api/v1/recipe")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetRecipeById() throws Exception {
        mockMvc.perform(get("/api/v1/recipe/{recipeId}", "101")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldGetRecipeByName() throws Exception {
        mockMvc.perform(get("/api/v1/recipe/name/{name}", "test4recipe")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteRecipeById() throws Exception {
        mockMvc.perform(get("/api/v1/recipe/{recipeId}", "104")).
                andExpect(status().isOk())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}

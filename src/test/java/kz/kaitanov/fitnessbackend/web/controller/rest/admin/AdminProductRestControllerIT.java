package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateRequestDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminProductRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/saveProduct_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/saveProduct_SuccessfulTest/AfterTest.sql")
    public void saveProduct_SuccessfulTest() throws Exception {
        ProductPersistRequestDto dto = new ProductPersistRequestDto("Onion", 20, 3, 0, 1);
        String token = getToken("username", "password");
        mockMvc.perform(post("/api/v1/admin/product")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(dto.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.calorie", Is.is(dto.calorie())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.protein", Is.is(dto.protein())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fat", Is.is(dto.fat())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.carbohydrate", Is.is(dto.carbohydrate())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) > 0
                                FROM Product p
                                WHERE p.name = :name AND p.calorie = :calorie AND p.protein = :protein AND p.fat = :fat
                                AND p.carbohydrate = :carbohydrate
                                  """,
                        Boolean.class)
                .setParameter("name", dto.name())
                .setParameter("calorie", dto.calorie())
                .setParameter("protein", dto.protein())
                .setParameter("fat", dto.fat())
                .setParameter("carbohydrate", dto.carbohydrate())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProduct_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProduct_SuccessfulTest/AfterTest.sql")
    public void updateProduct_SuccessfulTest() throws Exception {
        ProductUpdateRequestDto dto = new ProductUpdateRequestDto(101L, 200, 100, 50, 25);
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/product")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.calorie", Is.is(dto.calorie())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.protein", Is.is(dto.protein())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fat", Is.is(dto.fat())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.carbohydrate", Is.is(dto.carbohydrate())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) > 0
                                FROM Product p
                                WHERE p.id = :id AND p.calorie = :calorie AND p.protein = :protein AND p.fat = :fat
                                AND p.carbohydrate = :carbohydrate
                                  """,
                        Boolean.class)
                .setParameter("id", dto.id())
                .setParameter("calorie", dto.calorie())
                .setParameter("protein", dto.protein())
                .setParameter("fat", dto.fat())
                .setParameter("carbohydrate", dto.carbohydrate())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_SuccessfulTest/AfterTest.sql")
    public void updateProductName_SuccessfulTest() throws Exception {
        ProductUpdateNameRequestDto dto = new ProductUpdateNameRequestDto(101L, "Carrot");
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/product/name")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(dto.name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) > 0
                                FROM Product p
                                WHERE p.name = :name AND p.id = :id
                           """,
                        Boolean.class)
                .setParameter("name", dto.name())
                .setParameter("id", dto.id())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductList_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductList_SuccessfulTest/AfterTest.sql")
    public void getProductList_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/product")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].id", hasItems(101,102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].name", hasItems("Onion", "Carrot")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].calorie", hasItems(20, 55)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].protein", hasItems(25, 13)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].fat", hasItems(1, 1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].carbohydrate", hasItems(5, 1)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_SuccessfulTest/AfterTest.sql")
    public void getProductById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/product/{productId}", 101)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",Is.is("Onion")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.calorie", Is.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.protein", Is.is(25)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fat", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.carbohydrate", Is.is(5)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_SuccessfulTest/AfterTest.sql")
    public void getProductByName_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/product/productName/{productName}", "Onion")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",Is.is("Onion")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.calorie", Is.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.protein", Is.is(25)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fat", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.carbohydrate", Is.is(5)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/deleteProductById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/deleteProductById_SuccessfulTest/AfterTest.sql")
    public void deleteProductById_SuccessfulTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/product/{productId}", 101)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) > 0
                                FROM Product p
                                WHERE p.id = :id
                           """,
                        Boolean.class)
                .setParameter("id", 101L)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/saveProduct_WithExistingNameTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/saveProduct_WithExistingNameTest/AfterTest.sql")
    public void saveProduct_WithExistingNameTest() throws Exception {
        ProductPersistRequestDto dto = new ProductPersistRequestDto("Onion", 20, 3, 0, 1);
        String token = getToken("username", "password");
        mockMvc.perform(post("/api/v1/admin/product")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Name is being used by another product")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProduct_WithNonExistingIdTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProduct_WithNonExistingIdTest/AfterTest.sql")
    public void updateProduct_WithNonExistingIdTest() throws Exception {
        ProductUpdateRequestDto dto = new ProductUpdateRequestDto( 102L, 200, 200, 200, 200);
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/product")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_WithTheSameNameTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_WithTheSameNameTest/AfterTest.sql")
    public void updateProductName_WithTheSameNameTest() throws Exception {
        ProductUpdateNameRequestDto dto = new ProductUpdateNameRequestDto(101L, "Onion");
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/product/name")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Name is being used by another product")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_WithNonExistingIdTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/updateProductName_WithNonExistingIdTest/AfterTest.sql")
    public void updateProductName_WithNonExistingIdTest() throws Exception {
        ProductUpdateNameRequestDto dto = new ProductUpdateNameRequestDto(102L, "Carrot");
        String token = getToken("username", "password");
        mockMvc.perform(put("/api/v1/admin/product/name")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product with id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_WithNonExistingIdTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductById_WithNonExistingIdTest/AfterTest.sql")
   public void  getProductById_WithNonExistingIdTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/product/{productId}", 102)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product with id 102 not found")));
   }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductByName_WithNonExistingNameTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/getProductByName_WithNonExistingNameTest/AfterTest.sql")
    public void  getProductByName_WithNonExistingNameTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(get("/api/v1/admin/product/productName/{productName}", "Carrot")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product with name Carrot not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/deleteProductById_WithNonExistingIdTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminProductRestController/deleteProductById_WithNonExistingIdTest/AfterTest.sql")
    public void  deleteProductById_WithNonExistingIdTest() throws Exception {
        String token = getToken("username", "password");
        mockMvc.perform(delete("/api/v1/admin/product/{productId}", 102)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product with id 102 not found")));
    }
}






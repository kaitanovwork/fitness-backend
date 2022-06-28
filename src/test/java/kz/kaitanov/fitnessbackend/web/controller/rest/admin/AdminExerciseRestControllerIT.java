package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExercisePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.enums.Area;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminExerciseRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExercisePage_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExercisePage_SuccessfulTest/AfterTest.sql")
    public void getExercisePage_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");

        mockMvc.perform(get("/api/v1/admin/exercise")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].name", hasItems("TestExercise1", "TestExercise2", "TestExercise3", "TestExercise4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].muscleGroup", hasItems("Biceps", "Legs", "Triceps", "Shoulders")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].repeatCount", hasItems(10, 12, 12, 15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].approachCount", hasItems(10, 12, 8, 6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].area", hasItems("HOME", "GYM", "HOME", "GYM")));

        mockMvc.perform(get("/api/v1/admin/exercise?page=1&size=5&sort=name,desc")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements", Is.is(0)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/saveExercise_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/saveExercise_SuccessfulTest/AfterTest.sql")
    public void saveExercise_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");
        ExercisePersistRequestDto dto = new ExercisePersistRequestDto("push", "back", 3, 10, Area.GYM);

        mockMvc.perform(post("/api/v1/admin/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(dto.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.muscleGroup", Is.is(dto.muscleGroup())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.repeatCount", Is.is(dto.repeatCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.approachCount", Is.is(dto.approachCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.area", Is.is(dto.area().name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(e.id) > 0
                                FROM Exercise e
                                WHERE e.name = :name AND e.muscleGroup = :muscleGroup AND e.repeatCount = :repeatCount AND e.approachCount = :approachCount
                                AND e.area = :area
                                """,
                        Boolean.class)
                .setParameter("name", dto.name())
                .setParameter("muscleGroup", dto.muscleGroup())
                .setParameter("repeatCount", dto.repeatCount())
                .setParameter("approachCount", dto.approachCount())
                .setParameter("area", dto.area())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/saveExercise_WithExistingNameTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/saveExercise_WithExistingNameTest/AfterTest.sql")
    public void saveExercise_WithExistingNameTest() throws Exception {
        String token = getToken("username", "pass");
        ExercisePersistRequestDto dto = new ExercisePersistRequestDto("TestExercise1", "back", 3, 10, Area.GYM);

        mockMvc.perform(post("/api/v1/admin/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("name is being used by another exercise")));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_SuccessfulTest/AfterTest.sql")
    public void updateExercise_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");
        ExerciseUpdateRequestDto exerciseUpdateRequestDto = new ExerciseUpdateRequestDto(101L, "Biceps", 20, 15, Area.HOME);

        mockMvc.perform(put("/api/v1/admin/exercise")
                        .header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(exerciseUpdateRequestDto.id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.muscleGroup", Is.is(exerciseUpdateRequestDto.muscleGroup())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.repeatCount", Is.is(exerciseUpdateRequestDto.repeatCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.approachCount", Is.is(exerciseUpdateRequestDto.approachCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.area", Is.is(exerciseUpdateRequestDto.area().name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.id = :id AND u.muscleGroup = :muscleGroup AND u.repeatCount = :repeatCount AND u.approachCount = :approachCount
                                AND u.area = :area""",
                        Boolean.class)
                .setParameter("id", exerciseUpdateRequestDto.id())
                .setParameter("muscleGroup", exerciseUpdateRequestDto.muscleGroup())
                .setParameter("repeatCount", exerciseUpdateRequestDto.repeatCount())
                .setParameter("approachCount", exerciseUpdateRequestDto.approachCount())
                .setParameter("area", exerciseUpdateRequestDto.area())
                .getSingleResult());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_WithNotExistingExerciseTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_WithNotExistingExerciseTest/AfterTest.sql")
    public void updateExercise_WithNotExistingExerciseTest() throws Exception {

        String token = getToken("username", "pass");
        ExerciseUpdateRequestDto exerciseUpdateRequestDto = new ExerciseUpdateRequestDto(102L, "Biceps", 20, 15, Area.HOME);

        mockMvc.perform(put("/api/v1/admin/exercise")
                        .header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Exercise by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExerciseName_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExerciseName_SuccessfulTest/AfterTest.sql")
    public void updateExerciseName_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");
        ExerciseUpdateNameRequestDto exerciseUpdateNameRequestDto = new ExerciseUpdateNameRequestDto(101L, "TestExercise");

        mockMvc.perform(put("/api/v1/admin/exercise/name")
                        .header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseUpdateNameRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(exerciseUpdateNameRequestDto.id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(exerciseUpdateNameRequestDto.name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.id = :id AND u.name = :name""",
                        Boolean.class)
                .setParameter("id", exerciseUpdateNameRequestDto.id())
                .setParameter("name", exerciseUpdateNameRequestDto.name())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_WithNotExistingExerciseTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/updateExercise_WithNotExistingExerciseTest/AfterTest.sql")
    public void updateExerciseName_WithNotExistingExerciseTest() throws Exception {
        String token = getToken("username", "pass");
        ExerciseUpdateNameRequestDto exerciseUpdateNameRequestDto = new ExerciseUpdateNameRequestDto(102L, "TestExercise");

        mockMvc.perform(put("/api/v1/admin/exercise/name")
                        .header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseUpdateNameRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Exercise by id 102 not found")));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExerciseById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExerciseById_SuccessfulTest/AfterTest.sql")
    public void getExerciseById_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");
        Exercise exercise = new Exercise(101L, "TestExercise1", "Biceps", 10, 10, Area.HOME);

        mockMvc.perform(get("/api/v1/admin/exercise/{exerciseId}", "101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(exercise.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.muscleGroup", Is.is(exercise.getMuscleGroup())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.repeatCount", Is.is(exercise.getRepeatCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.approachCount", Is.is(exercise.getApproachCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.area", Is.is(exercise.getArea().name())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExerciseById_WithNotExistingExerciseTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/getExerciseById_WithNotExistingExerciseTest/AfterTest.sql")
    public void getExerciseById_WithNotExistingExerciseTest() throws Exception {
        String token = getToken("username", "pass");

        mockMvc.perform(get("/api/v1/admin/exercise/{exerciseId}", "102")
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Exercise by id 102 not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/deleteExerciseById_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/deleteExerciseById_SuccessfulTest/AfterTest.sql")
    public void deleteExerciseById_SuccessfulTest() throws Exception {
        String token = getToken("username", "pass");

        mockMvc.perform(delete("/api/v1/admin/exercise/{exerciseId}", "101")
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.id = :id""",
                        Boolean.class)
                .setParameter("id", 101L)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/deleteExerciseById_WithNotExistingExerciseTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/admin/AdminExerciseRestController/deleteExerciseById_WithNotExistingExerciseTest/AfterTest.sql")
    public void deleteExerciseById_WithNotExistingExerciseTest() throws Exception {
        String token = getToken("username", "pass");

        mockMvc.perform(delete("/api/v1/admin/exercise/{exerciseId}", "102")
                        .header("authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Exercise by id 102 not found")));
    }

}

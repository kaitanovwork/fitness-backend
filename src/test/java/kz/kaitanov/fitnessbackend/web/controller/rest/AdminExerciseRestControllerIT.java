package kz.kaitanov.fitnessbackend.web.controller.rest;


import kz.kaitanov.fitnessbackend.SpringSimpleContextTest;
import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.dto.request.ExercisePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.JwtRequest;
import kz.kaitanov.fitnessbackend.model.enums.Area;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.Is;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
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


@RequiredArgsConstructor
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/ExerciseRestController/create-exercise-before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/ExerciseRestController/clear-exercise-after.sql")
public class AdminExerciseRestControllerIT extends SpringSimpleContextTest {


    @Test
    void shouldGetExerciseList() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(get("/api/v1/admin/exercise")
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].name", hasItems("TestExercise1", "TestExercise2", "TestExercise3", "TestExercise4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].muscleGroup", hasItems("Biceps", "Legs", "Triceps", "Shoulders")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].repeatCount", hasItems(10, 12, 12, 15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].approachCount", hasItems(10, 12, 8, 6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[*].area", hasItems("HOME", "GYM", "HOME", "GYM")));
    }

    @Test
    void shouldSaveExercise() throws Exception {
        ExercisePersistRequestDto dto = new ExercisePersistRequestDto("push", "back", 3, 10, Area.GYM);
        String jsonExercise = objectMapper.writeValueAsString(dto);

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(post("/api/v1/admin/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExercise)
                        .header("authorization", "Bearer " + token))
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
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.name = :name AND u.muscleGroup = :muscleGroup AND u.repeatCount = :repeatCount AND u.approachCount = :approachCount
                                AND u.area = :area""",
                        Boolean.class)
                .setParameter("name", dto.name())
                .setParameter("muscleGroup", dto.muscleGroup())
                .setParameter("repeatCount", dto.repeatCount())
                .setParameter("approachCount", dto.approachCount())
                .setParameter("area", dto.area())
                .getSingleResult());
    }


    @Test
    void shouldUpdateExercise() throws Exception {

        Exercise exercise = new Exercise();
        exercise.setId(101L);
        exercise.setApproachCount(15);
        exercise.setMuscleGroup("Biceps");
        exercise.setRepeatCount(20);
        exercise.setName("TestExercise1");
        exercise.setArea(Area.HOME);

        String jsonExercise = objectMapper.writeValueAsString(exercise);

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(put("/api/v1/admin/exercise")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExercise))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Is.is(exercise.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.muscleGroup", Is.is(exercise.getMuscleGroup())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.repeatCount", Is.is(exercise.getRepeatCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.approachCount", Is.is(exercise.getApproachCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.area", Is.is(exercise.getArea().name())));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.name = :name AND u.muscleGroup = :muscleGroup AND u.repeatCount = :repeatCount AND u.approachCount = :approachCount
                                AND u.area = :area""",
                        Boolean.class)
                .setParameter("name", exercise.getName())
                .setParameter("muscleGroup", exercise.getMuscleGroup())
                .setParameter("repeatCount", exercise.getRepeatCount())
                .setParameter("approachCount", exercise.getApproachCount())
                .setParameter("area", exercise.getArea())
                .getSingleResult());

    }

    @Test
    void shouldUpdateExerciseNotFoundExercise() throws Exception {

        Exercise exercise = new Exercise();
        exercise.setId(105L);
        exercise.setApproachCount(15);
        exercise.setMuscleGroup("Biceps");
        exercise.setRepeatCount(20);
        exercise.setName("TestExercise1");
        exercise.setArea(Area.HOME);

        String jsonExercise = objectMapper.writeValueAsString(exercise);

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(put("/api/v1/admin/exercise")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExercise))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }


    @Test
    void shouldGetExerciseById() throws Exception {

        Exercise exercise = new Exercise();
        exercise.setId(101L);
        exercise.setApproachCount(10);
        exercise.setMuscleGroup("Biceps");
        exercise.setRepeatCount(10);
        exercise.setName("TestExercise1");
        exercise.setArea(Area.HOME);

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");


        mockMvc.perform(get("/api/v1/admin/exercise/{exerciseId}", "101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + token))
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
    void shouldGetExerciseByIdNotFound() throws Exception {


        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");


        mockMvc.perform(get("/api/v1/admin/exercise/{exerciseId}", "105")
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }

    @Test
    void shouldDeleteExerciseByID() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(delete("/api/v1/admin/exercise/{exerciseId}", "104")
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(u.id) > 0
                                FROM Exercise u
                                WHERE u.id = :id""",
                        Boolean.class)
                .setParameter("id", 104L)
                .getSingleResult());
    }

    @Test
    void shouldDeleteExerciseByIDifNotFound() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("username", "pass");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        mockMvc.perform(delete("/api/v1/admin/exercise/{exerciseId}", "105")
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)));
    }

}

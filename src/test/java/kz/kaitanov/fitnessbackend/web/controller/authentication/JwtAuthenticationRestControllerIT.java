package kz.kaitanov.fitnessbackend.web.controller.authentication;


import kz.kaitanov.fitnessbackend.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
@RequiredArgsConstructor
public class JwtAuthenticationRestControllerIT {
    @Autowired
    private final MockMvc mockMvc;
    @Autowired
    private final JwtAuthenticationRestController controller;

    @Test
    void shouldCreateAuthenticationToken() throws Exception {
        mockMvc.perform(post("/api/v1/authenticate"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

package kz.kaitanov.fitnessbackend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@WithMockUser(username = "user101", password = "1", authorities = "ADMIN")
public class SpringSimpleContextTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
}

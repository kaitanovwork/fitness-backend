package kz.kaitanov.fitnessbackend.web.controller.handler;


import kz.kaitanov.fitnessbackend.annotation.IT;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IT
@AllArgsConstructor
public class GlobalRestControllerExceptionHandlerIT {
    @Autowired
    private final MockMvc mockMvc;
    @Autowired
    private final GlobalRestControllerExceptionHandler exceptionHandler;

    @Test
    void test() throws Exception{

    }
}

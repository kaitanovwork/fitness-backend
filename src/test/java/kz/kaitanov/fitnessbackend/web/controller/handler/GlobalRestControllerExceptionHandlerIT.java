package kz.kaitanov.fitnessbackend.web.controller.handler;


import kz.kaitanov.fitnessbackend.annotation.IT;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

@IT
@AllArgsConstructor
public class GlobalRestControllerExceptionHandlerIT {

    private final MockMvc mockMvc;
    private final GlobalRestControllerExceptionHandler exceptionHandler;

    @Test
    void test() throws Exception{

    }
}

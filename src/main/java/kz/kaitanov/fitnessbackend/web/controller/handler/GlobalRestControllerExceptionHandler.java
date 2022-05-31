package kz.kaitanov.fitnessbackend.web.controller.handler;

import kz.kaitanov.fitnessbackend.exception.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Void> usernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Void> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> jwtException(JwtException exception) {
        return ResponseEntity.badRequest().build();
    }
}

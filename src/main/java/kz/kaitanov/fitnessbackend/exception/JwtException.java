package kz.kaitanov.fitnessbackend.exception;

public class JwtException extends RuntimeException{

    public JwtException(String message) {
        super(message);
    }
}

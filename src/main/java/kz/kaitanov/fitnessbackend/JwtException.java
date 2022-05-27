package kz.kaitanov.fitnessbackend;

public class JwtException extends RuntimeException{

    public JwtException(String message) {
        super(message);
    }
}

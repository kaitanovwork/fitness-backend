package kz.kaitanov.fitnessbackend.web.config.util;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.function.Supplier;

public final class ApiValidationUtil {

    public static void requireTrue(boolean value, String message) {
        if (!value) {
            throw validationExceptionSupplier(message).get();
        }
    }

    public static void requireFalse(boolean value, String message) {
        if (value) {
            throw validationExceptionSupplier(message).get();
        }
    }

    private static Supplier<ConstraintViolationException> validationExceptionSupplier(String message) {
        return () -> new ConstraintViolationException(message, Collections.emptySet());
    }
}

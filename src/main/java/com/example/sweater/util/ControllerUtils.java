package com.example.sweater.util;

import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ControllerUtils {

    public static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error", fieldError -> {
                    String defaultMessage = fieldError.getDefaultMessage();
                    return Objects.requireNonNullElse(defaultMessage, "Сообщение об ошибке отсутствует");
                }
        ));
    }
}

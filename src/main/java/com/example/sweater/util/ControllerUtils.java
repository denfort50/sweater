package com.example.sweater.util;

import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Утилитный класс для обработки ошибок
 *
 * @author Denis Kalchenko
 */
public class ControllerUtils {

    /**
     * Метод для вывода сообщения об ошибках
     *
     * @param bindingResult объект для проверки ошибок
     * @return ассоциативный массив - название ошибки и сообщение
     */
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error", fieldError -> {
                    String defaultMessage = fieldError.getDefaultMessage();
                    return Objects.requireNonNullElse(defaultMessage, "Сообщение об ошибке отсутствует");
                }
        ));
    }
}

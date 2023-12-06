package com.example.sweater.controller;

import com.example.sweater.dto.CaptchaResponseDto;
import com.example.sweater.model.User;
import com.example.sweater.service.UserService;
import com.example.sweater.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Контроллер для работы с процессом регистрации пользователей
 *
 * @author Denis Kalchenko
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.url}")
    private String captchaUrl;

    @Value("${recaptcha.secret}")
    private String secret;

    /**
     * Метод для обработки запроса на показ web-формы с регистрацией
     *
     * @return страницу регистрации
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Метод для обработки запроса на регистрацию пользователя
     *
     * @param passwordConfirmation подтвержденный пароль
     * @param captchaResponse      код капчи
     * @param user                 новый пользователь
     * @param bindingResult        объект для проверки ошибок
     * @param model                модель
     * @return страницу авторизации
     */
    @PostMapping("/registration")
    public String addUser(@RequestParam("password2") String passwordConfirmation,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user, BindingResult bindingResult, Model model) {
        String url = String.format(captchaUrl, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate
                .postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!Objects.requireNonNull(response).isSuccess()) {
            model.addAttribute("captchaError", "Заполните капчу");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Пароль подтверждения не может быть пустым");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
        }
        if (isConfirmEmpty || bindingResult.hasErrors() || !Objects.requireNonNull(response).isSuccess()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Такой пользователь существует!");
            return "registration";
        }
        return "redirect:/login";
    }

    /**
     * Метод выводит информацию о статусе активации пользователя
     *
     * @param model модель
     * @param code  код активации
     * @return страницу авторизации
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден!");
        }
        return "login";
    }
}

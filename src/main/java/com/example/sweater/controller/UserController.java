package com.example.sweater.controller;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Контроллер для работы с процессом авторизации пользователей
 *
 * @author Denis Kalchenko
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Метод для обработки запроса на показ списка пользователей
     *
     * @param currentUser текущий авторизованный пользователь
     * @param model       модель
     * @return страницу со списком пользователей
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", currentUser);
        return "userList";
    }

    /**
     * Метод для обработки запроса на показ страницы с формой редактирования прав пользователя
     *
     * @param user  пользователь
     * @param model модель
     * @return страницу с формой редактирования прав пользователя
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    /**
     * Метод для обработки запроса на изменение прав пользователя
     *
     * @param username имя пользователя
     * @param form     перечень прав
     * @param user     пользователь
     * @return страницу со списком пользователей
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam String username, @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    /**
     * Метод для обработки запроса на показ страницы с формой редактирования профиля
     *
     * @param model модель
     * @param user  пользователь
     * @return страницу с формой редактирования профиля
     */
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("user", user);
        return "profile";
    }

    /**
     * Метод для обработки запроса на изменение данных профиля
     *
     * @param user     пользователь
     * @param password пароль
     * @param email    почта
     * @return страницу с формой редактирования профиля
     */
    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    /**
     * Метод для обработки запроса подписки на пользователя
     *
     * @param currentUser текущий авторизованный пользователь
     * @param user        пользователь, на кого подписываемся
     * @return страницу с сообщениями конкретного пользователя
     */
    @GetMapping("subscribe/{user}")
    public String subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable User user) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    /**
     * Метод для обработки запроса отписки от пользователя
     *
     * @param currentUser текущий авторизованный пользователь
     * @param user        пользователь, от кого отписываемся
     * @return страницу с сообщениями конкретного пользователя
     */
    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@AuthenticationPrincipal User currentUser,
                              @PathVariable User user) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    /**
     * Метод для обработки запроса на показ страницы с подписками и подписчиками конкретного пользователя
     *
     * @param user  пользователь
     * @param type  тип: подписки / подписчики
     * @param model модель
     * @return страницу с подписками / подписчиками
     */
    @GetMapping("{type}/{user}/list")
    public String userList(@PathVariable User user, @PathVariable String type, Model model) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }
}

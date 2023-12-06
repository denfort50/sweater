package com.example.sweater.controller;

import com.example.sweater.dto.MessageDto;
import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.service.MessageService;
import com.example.sweater.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Контроллер для обработки событий с сообщениями
 *
 * @author Denis Kalchenko
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Метод для обработки запроса на показ страницы приветствия
     *
     * @param currentUser текущий авторизованный пользователь
     * @param model       модель
     * @return страницу приветствия
     */
    @GetMapping("/")
    public String greetingPage(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        return "greeting";
    }

    /**
     * Метод для обработки запроса на показ страницы со всеми сообщениями
     *
     * @param currentUser текущий авторизованный пользователь
     * @param filter      строка, по которой осуществляется поиск сообщений
     * @param model       модель
     * @param pageable    объект с информацией о пагинации
     * @return страницу с сообщениями
     */
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User currentUser,
                       @RequestParam(required = false) String filter, Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MessageDto> page = messageService.messageList(filter, pageable, currentUser);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        model.addAttribute("user", currentUser);
        return "main";
    }

    /**
     * Метод для обработки запроса на запись сообщения
     *
     * @param user          текущий авторизованный пользователь
     * @param message       сообщение
     * @param bindingResult объект для проверки ошибок
     * @param model         модель
     * @param file          картинка
     * @param pageable      объект с информацией о пагинации
     * @return страницу с сообщениями
     * @throws IOException в случае ошибки считывания файла-картинки
     */
    @PostMapping("/main")
    public String addMessage(@AuthenticationPrincipal User user, @Valid Message message,
                             BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable)
            throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageService.save(message);
        }
        Page<MessageDto> page = messageService.findAll(pageable, user);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("user", user);
        return "main";
    }

    /**
     * Метод для обработки запроса на показ сообщений конкретного пользователя
     *
     * @param currentUser текущий авторизованный пользователь
     * @param author      автор сообщения
     * @param model       модель
     * @param message     сообщение
     * @param pageable    объект с информацией о пагинации
     * @return страницу с сообщениями конкретного пользователя
     */
    @GetMapping("/user-messages/{author}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User author, Model model,
                               @RequestParam(required = false) Message message,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MessageDto> page = messageService.messageListForUser(pageable, author, currentUser);
        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-messages/{user}");
        return "parts/userMessages";
    }

    /**
     * Метод для обработки запроса на обновления сообщения текущего пользователя
     *
     * @param currentUser текущий авторизованный пользователь
     * @param user        id пользователя
     * @param message     сообщение
     * @param text        текст сообщения
     * @param tag         тег сообщения
     * @param file        картинка
     * @return страницу с сообщениями конкретного пользователя
     * @throws IOException в случае ошибки считывания файла-картинки
     */
    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable Long user,
                                @RequestParam(name = "id", required = false) Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);

            }
            messageService.save(message);
            saveFile(message, file);
        }
        return "redirect:/user-messages/" + user;
    }

    /**
     * Метод для обработки запроса на лайк/дизлайк конкретному сообщению
     *
     * @param currentUser        текущий авторизованный пользователь
     * @param message            сообщение
     * @param redirectAttributes атрибуты для редиректа
     * @param referer            заголовок "referer"
     * @return текущую страницу с актуализированным числом лайков
     */
    @GetMapping("messages/{message}/like")
    public String like(@AuthenticationPrincipal User currentUser, @PathVariable Message message,
                       RedirectAttributes redirectAttributes, @RequestHeader(required = false) String referer) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(referer).build();
        uriComponents.getQueryParams().forEach(redirectAttributes::addAttribute);
        return "redirect:" + uriComponents.getPath();
    }

    /**
     * Метод для сохранения картинки в директорию проекта
     *
     * @param message сообщение
     * @param file    картинка
     * @throws IOException в случае ошибки считывания файла-картинки
     */
    private void saveFile(Message message, MultipartFile file) throws IOException {
        if (!file.isEmpty() && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }
}

# Sweater

## Описание проекта
Проект представляет собой аналог Твиттера.
* Использование сервиса возможно только после регистрации и авторизации. 
* В системе есть пользователи и администраторы. Пользователи могут добавлять и редактировать сообщения, указывать 
хэштеги, прикреплять картинки, ставить лайки постам, подписываться на других пользователей и отменять подписку. 
* Пользователям доступен просмотр как всей ленты сообщений, так и ленты сообщений конкретных пользователей. 
* Администратор может просматривать статистику по пользователям сервиса, выдавать/отбирать права администратора.

## Стек технологий
* Java 17
* Apache Maven 3.8.5
* Docker Compose 3.9
* PostgreSQL 14
* Flyway 8.5.13
* Spring Boot 2.7.17
* Spring MVC 5.3.30
* Spring Data JPA 2.7.17
* Hibernate 5.6.15.Final
* Spring Security 5.7.11
* Freemarker 2.3.32
* Tomcat 9.0.82
* Lombok 1.18.30
* Checkstyle 9.0
* JaCoCo 0.8.11

## Требования к окружению
* Браузер
* Docker

## Инструкция по запуску проекта
1) Клонировать проект `git clone https://github.com/denfort50/sweater`
2) В файле `application.properties` указать параметры **spring.mail.host**, **spring.mail.port**, 
**spring.mail.username** и **spring.mail.password** для работы почтового сервера, а также указать **recaptcha.url** и
**recaptcha.secret** для работы капчи
2) Перейти в папку с проектом `cd sweater`
3) Запустить сборку и запуск контейнеров `docker-compose up`
4) Перейти на `localhost` в браузере

## Взаимодействие с приложением

### При открытии ресурса попадаем на страницу приветствия
![img.jpg](img/greetingPage.png)

### Необходимо авторизоваться
![img.jpg](img/loginPage.png)

### Если учетной записи ещё нет, то регистрируемся
![img.jpg](img/registerPage.png)

### После регистрации высылается письмо с кодом подтверждения
![img.jpg](img/activationCode.png)

### Проходим по ссылке для активации пользователя
![img.jpg](img/successActivation.png)

### Проходим авторизацию и попадаем на страницу с сообщениями
![img.jpg](img/paginationOne.png)

### В сервисе реализована пагинация
![img.jpg](img/paginationTwo.png)

### Пагинация позволяет просматривать сообщения выборками, а не одним огромным списком
![img.jpg](img/paginationThree.png)

### Форма добавления нового поста
![img.jpg](img/messageEditor.png)

### Успешное добавление сообщения
![img.jpg](img/newMessageSuccess.png)

### Страница постов текущего пользователя и его подписки с подписчиками
![img.jpg](img/userMessages.png)

### Пользователь может посмотреть сообщения других пользователей и их подписки с подписчиками
![img.jpg](img/adminMessages.png)

### Можно "провалиться" к списку подписок / подписчиков
![img.jpg](img/subscribers.png)

### А также перейти отсюда на страницу к другому пользователю
![img.jpg](img/denisMessages.png)

### Есть возможность изменять пароль и почту
![img.jpg](img/changeProfile.png)

### Администратор может просматривать список зарегистрированных пользователей
![img.jpg](img/userList.png)

### Может изменить права пользователя
![img.jpg](img/editRights.png)

## Вариант дальнейшего развития проекта
1) Выделить frontend в отдельное приложение
2) Зарефакторить backend так, чтобы frontend получал данные в виде json-ответов, то есть реализовать RESTful API
3) Выделить логику авторизации и аутентификации в отдельный микросервис, взяв за основу OAuth2

## Контакты для связи
[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/kalchenko_denis)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:denfort50@yandex.ru)&nbsp;&nbsp;
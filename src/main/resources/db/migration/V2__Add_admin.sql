insert into users (username, password, active)
values ('admin', 'password', true);

insert into user_roles (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');
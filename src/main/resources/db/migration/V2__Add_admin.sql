insert into users (username, password, active, email)
values ('admin', 'password', true, 'admin@sweater.com');

insert into user_roles (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');
delete
from user_roles;

delete
from users;

insert into users (id, active, password, username)
values (1, true, '$2a$08$ntSmqQ/QB7DEgRLW9fmJc.xJOfNxQO16sR6Lr9zLrfeGnfbtKSrEq', 'admin'),
       (2, true, '$2a$08$ntSmqQ/QB7DEgRLW9fmJc.xJOfNxQO16sR6Lr9zLrfeGnfbtKSrEq', 'mike');

insert into user_roles(user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN'),
       (2, 'USER');
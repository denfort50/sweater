delete
from messages;

insert into messages (id, text, tag, user_id)
values (1, 'first', '#f1', 1),
       (2, 'second', '#s2', 1),
       (3, 'third', '#t3', 1),
       (4, 'fourth', '#f4', 1);

alter sequence messages_id_seq restart with 10;
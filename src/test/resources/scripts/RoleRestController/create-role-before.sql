truncate fitness_backend_db_test.public.users restart identity cascade;
truncate fitness_backend_db_test.public.roles restart identity cascade;


insert into fitness_backend_db_test.public.roles (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

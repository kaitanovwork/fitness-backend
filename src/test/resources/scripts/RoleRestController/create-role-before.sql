truncate fitness_backend_test_db.public.users restart identity cascade;
truncate fitness_backend_test_db.public.roles restart identity cascade;


insert into fitness_backend_test_db.public.roles (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

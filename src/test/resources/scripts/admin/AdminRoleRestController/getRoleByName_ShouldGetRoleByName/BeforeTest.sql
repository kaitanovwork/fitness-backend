truncate postgres.public.users restart identity cascade;
truncate postgres.public.roles restart identity cascade;


insert into postgres.public.roles (id, name)
VALUES (101, 'ADMIN'),
       (102, 'USER');

insert into postgres.public.users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
values (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName', '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101)

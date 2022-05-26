truncate fitness_backend_db_test.public.roles restart identity cascade;
truncate fitness_backend_db_test.public.users restart identity cascade;


insert into fitness_backend_db_test.public.roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

insert into fitness_backend_db_test.public.users(id, age, email, first_name, gender, last_name, password, phone,
                                                 username, role_id)
VALUES (101, 18, 'user101test@gmail.com', 'user101test', 'MALE', 'user101test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '89999999101', 'user101', 1),
       (102, 19, 'user102test@gmail.com', 'user102test', 'FEMALE', 'user2test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '89999999102', 'user102', 2),
       (103, 20, 'user103test@gmail.com', 'user103test', 'MALE', 'user103test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '89999999103', 'user103', 2),
       (104, 21, 'user104test@gmail.com', 'user104test', 'FEMALE', 'user104test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '89999999104', 'user104', 2);



-- Пароль от пользователя "1"
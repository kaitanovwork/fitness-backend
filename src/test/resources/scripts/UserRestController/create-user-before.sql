truncate fitness_backend_db_test.public.roles restart identity cascade;
truncate fitness_backend_db_test.public.users restart identity cascade;


insert into fitness_backend_db_test.public.roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

insert into fitness_backend_db_test.public.users(id, age, email, first_name, gender, last_name, password, phone,
                                                 username, role_id)
VALUES (101, 18, 'user1test@gmail.com', 'user1test', 'MALE', 'user1test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '8 999 999 9101', 'user101', 1),
       (102, 19, 'user2test@gmail.com', 'user2test', 'FEMALE', 'user2test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '8 999 999 9102', 'user102', 2),
       (103, 20, 'user3test@gmail.com', 'user3test', 'MALE', 'user3test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '8 999 999 9103', 'user103', 2),
       (104, 21, 'user4test@gmail.com', 'user4test', 'FEMALE', 'user4test',
        '$2a$12$k9IZTib7B3/I5EhV1EWV8.kCudhMexVmgDMISjaTyB0cIzrnZ5rbi', '8 999 999 9104', 'user104', 2);



-- Пароль от пользователя "1"
truncate fitness_backend_test_db.public.exercises RESTART IDENTITY;

TRUNCATE TABLE users CASCADE;

TRUNCATE TABLE roles CASCADE;

insert into fitness_backend_test_db.public.exercises (id, approach_count, area, muscle_group, name, repeat_count)
VALUES (101, 10, 'HOME', 'Biceps', 'TestExercise1', 10),
       (102, 12, 'GYM', 'Legs', 'TestExercise2', 12),
       (103, 8, 'HOME', 'Triceps', 'TestExercise3', 12),
       (104, 6, 'GYM', 'Shoulders', 'TestExercise4', 15);

INSERT INTO roles (id, name)
VALUES (130, 'ADMIN');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (105, 20, 'test@gmail.com', 'firstName', 'MALE', 'lastName', '$2a$10$DU7TvLkcHshv20dLxvYaK.jltbesPYufA.kaT/P2ig.g/9.Ygxg1y', '77028883322', 'username', 130);
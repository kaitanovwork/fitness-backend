INSERT INTO exercises (id, approach_count, area, name, repeat_count, category)
VALUES (101, 10, 'ARMS', 'TestExercise1', 10, 'STRENGTH'),
       (102, 12, 'LEGS', 'TestExercise2', 12, 'CARDIO'),
       (103, 8, 'BREAST', 'TestExercise3', 12, 'STRENGTH'),
       (104, 6, 'CHEST', 'TestExercise4', 15, 'CARDIO');

INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'test@gmail.com', 'firstName', 'MALE', 'lastName', '$2a$10$DU7TvLkcHshv20dLxvYaK.jltbesPYufA.kaT/P2ig.g/9.Ygxg1y', '77028883322', 'username', 101);
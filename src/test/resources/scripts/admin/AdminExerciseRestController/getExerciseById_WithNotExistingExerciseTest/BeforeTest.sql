INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'test@gmail.com', 'firstName', 'MALE', 'lastName', '$2a$10$DU7TvLkcHshv20dLxvYaK.jltbesPYufA.kaT/P2ig.g/9.Ygxg1y', '77028883322', 'username', 101);

INSERT INTO fitness_backend_test_db.public.exercises (id, approach_count, area, muscle_group, name, repeat_count, category)
VALUES (101, 10, 'HOME', 'Biceps', 'TestExercise1', 10, 'STRENGTH');

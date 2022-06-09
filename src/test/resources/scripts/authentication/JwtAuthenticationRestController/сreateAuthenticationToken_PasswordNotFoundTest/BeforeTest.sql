INSERT INTO roles (id, name)
VALUES (101, 'USER');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'test@gmail.com', 'firstName', 'MALE', 'lastName', '$2a$10$DU7TvLkcHshv20dLxvYaK.jltbesPYufA.kaT/P2ig.g/9.Ygxg1y', '77028883322', 'username', 101);
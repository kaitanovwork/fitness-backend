INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101);

INSERT INTO menus (id, program_type)
VALUES (101, 'WEIGHT_GAIN'),
       (102, 'WEIGHT_LOSS'),
       (103, 'WEIGHT_GAIN'),
       (104, 'WEIGHT_LOSS'),
       (105, 'WEIGHT_GAIN'),
       (106, 'WEIGHT_LOSS'),
       (107, 'WEIGHT_GAIN'),
       (108, 'WEIGHT_LOSS'),
       (109, 'WEIGHT_GAIN'),
       (110, 'WEIGHT_LOSS'),
       (111, 'WEIGHT_GAIN');

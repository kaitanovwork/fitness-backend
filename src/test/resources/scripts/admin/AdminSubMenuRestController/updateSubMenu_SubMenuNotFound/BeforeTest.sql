INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$TegbVVZY4yHFl4HH15k5Eedt8Ow5wARUMCOhWDy6tmYprZbLUzPkO', '111-00', 'username', 101);

INSERT INTO submenus (id, program_type, week_day)
VALUES (101, 'WEIGHT_GAIN', 'MONDAY');
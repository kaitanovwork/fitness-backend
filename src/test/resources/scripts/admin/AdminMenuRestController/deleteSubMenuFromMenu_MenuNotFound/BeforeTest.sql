INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101);

INSERT INTO fitness_backend_test_db.public.menus (id, program_type)
VALUES (101, 'WEIGHT_GAIN');

INSERT INTO fitness_backend_test_db.public.subMenus (id, program_type, week_day)
VALUES (101, 'WEIGHT_GAIN','WEDNESDAY');

INSERT INTO menus_subMenus (menus_id, subMenus_id)
VALUES (101, 101);
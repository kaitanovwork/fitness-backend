INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101);

INSERT INTO recipes (id, calorie, carbohydrate, description, fat, name, protein)
VALUES (101, 1500, 200, 'With chicken', 200, 'name', 200);

INSERT INTO products (id, calorie, carbohydrate, fat, name, protein)
VALUES (101, 500, 50, 50, 'Chicken', 50);

INSERT INTO recipes_products (recipes_id, products_id)
VALUES (101, 101);

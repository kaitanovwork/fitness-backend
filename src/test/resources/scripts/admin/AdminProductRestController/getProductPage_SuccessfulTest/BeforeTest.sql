INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101);

INSERT INTO products (id, name, calorie, protein, fat, carbohydrate)
VALUES (101, 'Onion', 20, 25, 1, 5),
       (102, 'Carrot', 55, 13, 8, 1),
       (103, 'Bread', 44, 13, 8, 1),
       (104, 'Potato', 33, 42, 7, 1),
       (105, 'Milk', 22, 13, 7, 1),
       (106, 'Cucumber', 24, 13, 7, 1),
       (107, 'Apple', 55, 13, 7, 1),
       (118, 'Banana6', 99, 43, 7, 24);




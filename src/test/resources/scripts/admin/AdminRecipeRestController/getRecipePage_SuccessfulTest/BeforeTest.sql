INSERT INTO roles (id, name)
VALUES (101, 'ADMIN');

INSERT INTO users (id, age, email, first_name, gender, last_name, password, phone, username, role_id)
VALUES (101, 20, 'email@email.ru', 'firstName', 'MALE', 'lastName',
        '$2a$12$WCVxn9WOBQceif5B5QQQ/eSgdQZx0eZVtD9h8EaeBa.PfN6uHUr/G', '111-00', 'username', 101);

INSERT INTO recipes (id, calorie, carbohydrate, description, fat, name, protein)
VALUES (101, 1500, 200, 'With chicken', 200, 'Caesar salad', 200),
       (102, 1600, 190, 'With beef', 300, 'Udon', 410),
       (103, 1700, 180, 'With beet', 200, 'PP', 420),
       (104, 1300, 170, 'With crab', 250, 'Crab salad', 430),
       (105, 1105, 160, 'With chickpeas', 100, 'Chickpea salad', 440),
       (106, 3784, 150, 'With avocado', 150, 'Salad with avocado and olives', 450),
       (107, 2314, 140, 'With cottage cheese', 50, 'Cottage cheese salad', 460),
       (108, 1000, 130, 'With tuna and potatoes', 350, 'Tuna salad', 470),
       (109, 1300, 120, 'With lettuce', 310, 'Lettuce salad', 460),
       (110, 1400, 110, 'With corn', 330, 'Salad with corn', 450),
       (111, 1500, 100, 'With spinach', 370, 'Spinach salad', 440);

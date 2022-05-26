truncate fitness_backend_db_test.public.recipes RESTART IDENTITY;

insert into fitness_backend_db_test.public.recipes(id, calorie, carbohydrate, fat, name, protein)
VALUES (101, 101, 101, 101, 'test1recipe', 101),
       (102, 102, 102, 102, 'test2recipe', 102),
       (103, 103, 103, 103, 'test3recipe', 103),
       (104, 104, 104, 104, 'test4recipe', 104);
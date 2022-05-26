truncate fitness_backend_db_test.public.exercises RESTART IDENTITY;

insert into fitness_backend_db_test.public.exercises (id, approach_count, area, muscle_group, name, repeat_count)
VALUES (101, 10, 'HOME', 'Biceps', 'TestExercise1', 10),
       (102, 12, 'GYM', 'Legs', 'TestExercise2', 12),
       (103, 8, 'HOME', 'Triceps', 'TestExercise3', 12),
       (104, 6, 'GYM', 'Shoulders', 'TestExercise4', 15);
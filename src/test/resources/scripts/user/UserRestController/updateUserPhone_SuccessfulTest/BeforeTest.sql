INSERT INTO roles(id, name)
VALUES (101, 'ADMIN'),
       (102, 'USER');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone,
                                                 username, role_id)
VALUES (103, 18, 'user101test@gmail.com', 'user101test', 'MALE', 'user103test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999101', 'user103', 101),
       (104, 19, 'user102test@gmail.com', 'user102test', 'FEMALE', 'user104test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999102', 'user104', 102),
       (105, 20, 'user103test@gmail.com', 'user103test', 'MALE', 'user105test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999103', 'user105', 102),
       (106, 21, 'user104test@gmail.com', 'user104test', 'FEMALE', 'user106test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999104', 'user106', 102);
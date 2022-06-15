INSERT INTO roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO users(id, age, email, first_name, gender, last_name, password, phone,
                                                 username, role_id)
VALUES (101, 18, 'user101test@gmail.com', 'user101test', 'MALE', 'user101test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999101', 'user101', 1),
       (102, 19, 'user102test@gmail.com', 'user102test', 'FEMALE', 'user2test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999102', 'user102', 2),
       (103, 20, 'user103test@gmail.com', 'user103test', 'MALE', 'user103test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999103', 'user103', 2),
       (104, 21, 'user104test@gmail.com', 'user104test', 'FEMALE', 'user104test',
        '$2a$12$TBgaQpyKsIKuo4oO5LA0EObD5MaBs77DHXo34nmZobnrtDMCl671S', '89999999104', 'user104', 2);
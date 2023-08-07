INSERT INTO users (id, email, username, firstname, lastname, birth_date)
VALUES
    (1, 'user1@example.com', 'user1', 'John', 'Doe', '1990-01-01'),
    (2, 'user2@example.com', 'user2', 'Jane', 'Smith', '1985-05-15'),
    (3, 'user3@example.com', 'user3', 'Mike', 'Johnson', '1992-11-30'),
    (4, 'user4@example.com', 'user4', 'Emily', 'Brown', '1988-07-20'),
    (5, 'user5@example.com', 'user5', 'Alex', 'Wilson', '1995-03-25'),
    (6, 'user6@example.com', 'user6', 'Sarah', 'Lee', '1983-09-10'),
    (7, 'user7@example.com', 'user7', 'Daniel', 'Martinez', '1998-02-05'),
    (8, 'user8@example.com', 'user8', 'Jessica', 'Taylor', '1993-12-18'),
    (9, 'user9@example.com', 'user9', 'Chris', 'Anderson', '1986-06-12'),
    (10, 'user10@example.com', 'user10', 'Laura', 'Harris', '1991-04-08');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));
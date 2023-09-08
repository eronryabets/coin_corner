INSERT INTO users (id, email, username, firstname, lastname, birth_date, account_non_locked, failed_attempt, lock_time)
VALUES (1, 'user1@example.com', 'user1', 'John', 'Doe', '1990-01-01', true, 0, null),
       (2, 'user2@example.com', 'user2', 'Jane', 'Smith', '1985-05-15', true, 0, null),
       (3, 'user3@example.com', 'user3', 'Mike', 'Johnson', '1992-11-30', true, 0, null),
       (4, 'user4@example.com', 'user4', 'Emily', 'Brown', '1988-07-20', true, 0, null),
       (5, 'user5@example.com', 'user5', 'Alex', 'Wilson', '1995-03-25', true, 0, null),
       (6, 'user6@example.com', 'user6', 'Sarah', 'Lee', '1983-09-10', true, 0, null),
       (7, 'user7@example.com', 'user7', 'Daniel', 'Martinez', '1998-02-05', true, 0, null),
       (8, 'user8@example.com', 'user8', 'Jessica', 'Taylor', '1993-12-18', true, 0, null),
       (9, 'user9@example.com', 'user9', 'Chris', 'Anderson', '1986-06-12', true, 0, null),
       (10, 'user10@example.com', 'user10', 'Laura', 'Harris', '1991-04-08', true, 0, null);
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO wallets (id, wallet_name, wallet_type, currency, balance, owner_user_id)
VALUES (1, 'Wallet 1', 'DEBIT', 'USD', 1000.00, 1),
       (2, 'Wallet 2', 'CREDIT', 'UAH', 1500.00, 1),
       (3, 'Wallet 3', 'DEBIT', 'USD', 300.00, 1),
       (4, 'Wallet 4', 'CREDIT', 'EUR', 1500.00, 2),
       (5, 'Wallet 5', 'CREDIT', 'UAH', 800.00, 2),
       (6, 'Wallet 6', 'CREDIT', 'PLN', 350.50, 2),
       (7, 'Wallet 7', 'CREDIT', 'EUR', 2200.00, 3),
       (8, 'Wallet 8', 'DEBIT', 'USD', 1000.00, 3),
       (9, 'Wallet 9', 'DEBIT', 'USD', 2000.00, 3),
       (10, 'Wallet 10', 'DEBIT', 'PLN', 700.00, 4),
       (11, 'Wallet 11', 'DEBIT', 'USD', 900.00, 4),
       (12, 'Wallet 12', 'CREDIT', 'PLN', 500.00, 4),
       (13, 'Wallet 13', 'CREDIT', 'USD', 800.00, 5),
       (14, 'Wallet 14', 'DEBIT', 'EUR', 1000.00, 5),
       (15, 'Wallet 15', 'DEBIT', 'PLN', 800.75, 5),
       (16, 'Wallet 16', 'CREDIT', 'EUR', 1500.55, 6);
SELECT SETVAL('wallets_id_seq', (SELECT MAX(id) FROM wallets));

INSERT INTO wallet_transactions (id, wallet_id, previous_balance, operation_type, transaction_date, amount, current_balance)
VALUES
    (1, 1, 1000.00, 'INCOME', '2020-09-30 14:30:00', 200.00, 1200.00),
    (2, 1, 1200.00, 'EXPENSE', '2020-10-15 15:45:00', -300.00, 900.00),
    (3, 1, 900.00, 'INCOME', '2020-11-18 16:20:00', 150.00, 1050.00),
    (4, 1, 1050.00, 'EXPENSE', '2020-12-05 17:10:00', -200.00, 850.00),
    (5, 1, 850.00, 'INCOME', '2021-05-07 18:05:00', 300.00, 1150.00),
    (6, 1, 1150.00, 'EXPENSE', '2021-07-15 19:30:00', -300.00, 850.00),

    (7, 2, 1500.00, 'EXPENSE', '2021-09-22 10:25:00', -100.00, 1400.00),
    (8, 2, 1500.00, 'INCOME', '2022-02-20 11:40:00', 300.00, 1800.00),
    (9, 2, 1800.00, 'EXPENSE', '2022-03-05 12:15:00', -500.00, 1300.00),
    (10, 2, 1300.00, 'INCOME', '2022-07-02 13:05:00', 200.00, 1500.00),
    (11, 2, 1500.00, 'EXPENSE', '2022-08-12 14:55:00', -600.00, 900.00),
    (12, 2, 900.00, 'INCOME', '2022-11-25 15:30:00', 100.00, 1000.00),

    (13, 3, 2000.00, 'INCOME', '2023-01-28 09:10:00', 300.00, 2300.00),
    (14, 4, 500.00, 'EXPENSE', '2023-04-10 10:20:00', -50.00, 450.00),
    (15, 5, 800.00, 'INCOME', '2023-06-08 11:15:00', 100.00, 900.00);

SELECT SETVAL('wallet_transactions_id_seq', (SELECT MAX(id) FROM wallet_transactions));

INSERT INTO roles (user_id, role)
SELECT id, 'USER' FROM users;

INSERT INTO roles (user_id,role)
VALUES (1,'USER');

INSERT INTO notes (id, text, date_added, user_id)
VALUES
    (1, 'Text 1', '2020-09-30 14:30:00', 1),
    (2, 'Text 2', '2021-05-07 18:05:00', 1),
    (3, 'Text 3', '2021-09-22 10:25:00', 2),
    (4, 'Text 4', '2022-02-11 13:30:00', 3),
    (5, 'Text 5', '2022-03-12 11:10:00', 4),
    (6, 'Text 6', '2022-04-20 12:20:00', 5),
    (7, 'Text 7', '2022-01-23 11:50:00', 6),
    (8, 'Text 8', '2023-02-11 11:10:00', 7),
    (9, 'Text 9', '2023-03-14 17:31:00', 8),
    (10, 'Text 10', '2023-04-11 12:30:00', 9),
    (11, 'Text 11', '2023-05-17 18:32:00', 10);

SELECT SETVAL('notes_id_seq', (SELECT MAX(id) FROM notes));

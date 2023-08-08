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

INSERT INTO wallets (id, wallet_name, wallet_type, currency, balance, owner_user_id)
VALUES
    (1, 'Wallet 1', 'DEBIT', 'USD', 1000.00, 1),
    (2, 'Wallet 2', 'CREDIT', 'EUR', 1500.00, 2),
    (3, 'Wallet 3', 'DEBIT', 'USD', 2000.00, 3),
    (4, 'Wallet 4', 'CREDIT', 'USD', 500.00, 4),
    (5, 'Wallet 5', 'DEBIT', 'EUR', 800.00, 5);
SELECT SETVAL('wallets_id_seq', (SELECT MAX(id) FROM wallets));

INSERT INTO wallet_transactions (id, wallet_id, previous_balance, operation_type, transaction_date, amount, current_balance)
VALUES
    (1, 1, 1000.00, 'INCOME', now(), 200.00, 1200.00),
    (2, 2, 1500.00, 'EXPENSE', now(), -100.00, 1400.00),
    (3, 3, 2000.00, 'INCOME', now(), 300.00, 2300.00),
    (4, 4, 500.00, 'EXPENSE', now(), -50.00, 450.00),
    (5, 5, 800.00, 'INCOME', now(), 100.00, 900.00);
SELECT SETVAL('wallet_transactions_id_seq', (SELECT MAX(id) FROM wallet_transactions));
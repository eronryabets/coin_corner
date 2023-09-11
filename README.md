# coin_corner
# Brief Description
Coin Corner - a pet project.
User's work with their finances through wallets (similar to bank cards).
User's personal notes. User's personal profile (roles, avatar).
Audit of financial operations, wallet incomes and expenses, interest rates on loans and savings wallets,
possibility to select a specific date for searching and calculating finances, filtering certain parameters for
searching, ability to download reports in formats: TXT, HTML, XML.
NBU exchange rates on the main page.

# Usage
User registration - entering personal data, uploading an avatar.
Possibility to reset the password - through a unique token sent to the email.
User access lock for one day after entering an incorrect password more than three times.
Remember me - saving user session for a long period.
Logging (date, username, ip): successful user authorization, unsuccessful authorization,
various system information of the application.

*Notes* Tab:
User notes - creating notes, tags (search by tags), pagination (navigation through pages, selecting the number of items per page),
editing notes, deletion.
Wallets - creating wallets, selecting the type of wallet (credit or savings), selecting the wallet's currency.

*Wallets* Tab:
Access to the list of user wallets with brief information about the wallets.
Transition to a specific wallet - updating wallet data.
Calculation of the wallet's interest rate for the selected period.
Wallet transactions - displaying financial operations of this wallet, filter by date,
and operation type (income, expense), paginated.
Cash transfer - transferring money to another wallet.
Replenishing the wallet balance.
Withdrawing money.

*Transactions* Tab:
Advanced search of user transactions, ability to select a specific wallet, or search
across all wallets, search by amounts, dates, operation type, by specific currencies. Pagination.

*Finances* Tab:
Calculation of financial data - transaction amount, income amount, expense amount.
Filter - by operation types, by currency, by wallet, by date.
There is an option to select a predefined date - current (or last) week, current (or last) month,
current (or last) quarter.
Saving the user report in TXT, HTML, XML formats.

*User Profile* Tab:
Editing user data, changing avatar, user roles.

*Main* Tab:
Displaying exchange rates according to NBU on the current day.

Admin Access:

*All Users* Tab:
Displaying the list of users, brief information, editing users, deletion.
Search filter by various parameters.

*All wallets* Tab:
Displaying the list of all user wallets with brief information.
Search filter by various parameters.

*All transactions* Tab:
Displaying the list of all transactions with brief information.
Search filter by various parameters.

# Technologies
Uses the latest version of Spring Boot 3.1 as of the project creation date (August 2023)
Gradle, Postgres, Data JPA, Querydsl, Liquibase, Docker Testcontainers, JPA Auditing
Hibernate Envers (revision table), Webmvc, Thymeleaf, Bootstrap, Swagger, Spring Security 6,
Ehcache, Caffeine, Mail, Validation, Mapstruct, Lombok, etc.

# More details:

*Mapstruct* - for mapping between entities and DTOs, with rare exceptions I created my own mappers.

*JPA Auditing* - used for auditing the wallets table, tracking who and when made changes.

*Hibernate Envers* - revision table wallets_aud (CustomRevisionListener) - contains data on all wallet transactions
balance before the operation, amount, operation type, new balance, etc.

*QueryDSL* - used with my QPredicates class to create dynamic queries, which were necessary for
filtering data for searching.

*Logback* - the project has three loggers, one for system data, the other two for recording successful and unsuccessful
user login attempts.

*Spring Security 6* - used to obtain user data on the front end (displaying one element or another -
authentication.principal), setting access to various admin pages at the service level, so that
the user cannot view another user's data by tampering with the URL parameters, etc.
User roles - User, Admin.

*Validation* - backend validation configuration, for various required data, creating custom
validators and annotations for validation.

*AOP* - used AOP to track wallet financial operations and record this data in the transaction table.
Also, after recording the transaction, to display a message about a successful transaction to the user, returned
transaction data in our Aspect using ThreadLocal.
Second-level cache - cached only financial transactions, as they are immutable.

*REST* - basic CRUD operations of entities, displaying avatars, displaying NBU exchange rates.

Liquibase - migration of tables, for testing a separate test container with its own data.sql was used.

*Remember me* - implemented long-term user session storage so that the session is preserved even after server restarts.
Token storage in the persistent_logins table.

*JavaMailSender* - if a user forgets their password, they can reset it. A unique token will be generated
and sent to the email, and this token will also be stored in the table. After that, the user
can follow the link with the token in the email and set a new password.

*SSL* - connected a local SSL certificate.
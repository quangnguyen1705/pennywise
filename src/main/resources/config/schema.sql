--Accounts database in SQLLite
CREATE TABLE IF NOT EXISTS accounts(
	id TEXT PRIMARY KEY, -- this wont be shown to the user
	bank_name TEXT NOT NULL, -- probably could have named this account_name instead, but this functionality still works
	open_balance DOUBLE NOT NULL,  
	open_date DATE NOT NULL
);

-- Transactions Database in SQLite
CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id TEXT NOT NULL,
    transaction_type TEXT NOT NULL,
    transaction_date DATE NOT NULL, 
    transaction_description TEXT NOT NULL,
    payment_amount DOUBLE NOT NULL DEFAULT 0,
    deposit_amount DOUBLE NOT NULL DEFAULT 0,
    CHECK ((payment_amount = 0 AND deposit_amount > 0) OR -- check that either payment_amount or deposit_amount is present, and not both
           (payment_amount > 0 AND deposit_amount = 0)),
    FOREIGN KEY (account_id) REFERENCES accounts(id) -- account_id has to reference an account in the accounts table 
    FOREIGN KEY (transaction_type) REFERENCES transaction_types(type_name) --valid transaction type
);


-- Transaction Types database in sqlite
CREATE TABLE IF NOT EXISTS transaction_types(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	type_name TEXT UNIQUE NOT NULL -- make sure it's a unqique name
)

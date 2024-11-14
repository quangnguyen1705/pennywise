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
    transaction_type_id INTEGER NOT NULL, 
    transaction_date DATE NOT NULL, 
    transaction_description TEXT NOT NULL,
    payment_amount DOUBLE NOT NULL DEFAULT 0,
    deposit_amount DOUBLE NOT NULL DEFAULT 0,
    
    CHECK ((payment_amount = 0 AND deposit_amount > 0) OR -- check that either payment_amount or deposit_amount is present, and not both
           (payment_amount > 0 AND deposit_amount = 0)),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE, -- account_id has to reference an account in the accounts table. also deleted transactions of an account if account is deleted. 
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_types(id) ON DELETE CASCADE --valid transaction type. deletes transactions of a transaction type if the transaction type is deleted. 
);


-- Transaction Types database in sqlite
CREATE TABLE IF NOT EXISTS transaction_types(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	type_name TEXT UNIQUE NOT NULL -- make sure it's a unqique name
);

-- Schedule transaction Type
CREATE TABLE IF NOT EXISTS scheduled_transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    schedule_name TEXT NOT NULL UNIQUE, 
    account_id TEXT NOT NULL, 
    transaction_type _id Integer NOT NULL, 
    frequency TEXT NOT NULL DEFAULT 'Monthly', 
    due_date INTEGER NOT NULL, 
    payment_amount DOUBLE NOT NULL, 
    
    CHECK (payment_amount > 0),
    
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE, -- deletes schedule transactions of account when account is deleted from accounts db
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_types(id) ON DELETE CASCADE-- deletes schedule transactions in schedule transactions db when its referenced transaction type is deleted
    
);

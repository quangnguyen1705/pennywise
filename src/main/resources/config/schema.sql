--Accounts database in SQLLite
CREATE TABLE IF NOT EXISTS accounts(
	id TEXT PRIMARY KEY, -- this wont be shown to the user
	bank_name TEXT NOT NULL, -- probably could have named this account_name instead, but this functionality still works
	open_balance DOUBLE NOT NULL,  
	open_date DATE NOT NULL
);

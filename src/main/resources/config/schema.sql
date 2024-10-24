--Accounts database in SQLLite
CREATE TABLE IF NOT EXISTS accounts(
	name TEXT PRIMARY KEY,
	open_balance DOUBLE NOT NULL,  
	open_date TEXT NOT NULL
);

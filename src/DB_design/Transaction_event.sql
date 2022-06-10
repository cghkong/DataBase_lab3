drop table if exists transaction_event;

create table transaction_event(
	user_id varchar(9) NOT NULL,
    user_id_f varchar(9) NOT NULL,
    money int NOT NULL
);

insert into transaction_event values
	('20220101','20220102',200),
	('20220102','20220106',300),
	('20220105','20220111',400),
	('20220105','20220108',150);
    

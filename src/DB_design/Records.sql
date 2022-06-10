drop table if exists records;

create table records(
	user_id varchar(9) NOT NULL,
    user_id_f varchar(9) NOT NULL,
    message varchar(150) NOT NULL
);

insert into records values
	('20220101','20220102','hello,how are you'),
	('20220101','20220106','Hi,nice to meet you'),
	('20220101','20220110','Hi,nice to meet you'),
	('20220101','20220108','Hi,nice to meet you'),
    
    ('20220108','20220101','Hi,nice to meet you too'),
     ('20220188','20220101','Hi,nice to meet you'),
     ('20220173','20220101','Hi,nice to meet you'),
    
    ('20220101','20220106','hello,where are you going'),
	('20220101','20220107','Hi,nice to meet you'),
	('20220105','20220111','Hi,nice to meet you'),
	('20220105','20220108','Hi,nice to meet you');
    
    
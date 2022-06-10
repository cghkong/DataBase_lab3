drop table if exists usercomments;

create table usercomments(
	user_id varchar(9) NOT NULL,
    user_topics varchar(45) NOT NULL,
	user_contents varchar(150) NOT NULL,
    user_click_num int default 1
); 

insert into usercomments values
    ('20220101','food','I ete a delicious tickey!',20),
    ('20220101','life','the weather is beautiful!.How about having a camping today?',10),
    ('20220101','study','I will go to college one month later!And I am exicited for that.',30),
    ('20220101','社会','去年11月，字节跳动调整公司组织架构，\n TikTok CEO周受资不再兼任字节跳动CFO后，这一职位已空缺5个月。',30),
    
    ('20220102','学习','I will work it out!',35),
    ('20220102','生活','today is nice',35),
    ('20220102','美食','I kike eating apples',35),
    ('20220104','study','I learned a lot today!',40),
    ('20220107','life','字节跳动CEO梁汝波在内部全员信中宣布，全球顶尖律所世达国际律师事务所（Skadden）高级合伙人高准（Julie Gao）将加入字节跳动，担任首席财务官（CFO），其主要办公地点在香港和新加坡',40),
    ('20220109','life','I watched a movie!',40),
    ('202201010','life','Good,it is nice to play badminton!',40);
create table client_types(
	id serial primary key,
	type varchar(32) not null,	-- кандидат, эксперт, компания
	unique(type)
);

insert into client_types(type) values('CANDIDATE');
insert into client_types(type) values('COMPANY');
insert into client_types(type) values('EXPERT');

create table account_types(  			-- описание платного аккаунта
	id serial primary key,
	name varchar(64) not null,
	description varchar(512),
	period int not null,				-- дни
	cost numeric not null				-- рубли
);

insert into account_types(name, description, period, cost) values('For free', 'Try Trial)', 30, 0);

create table user(
	id serial
	primary key,
	first_name varchar(64) not null,
	second_name varchar(64) not null,
	sur_name varchar(64),
	email_addr varchar(128),
	date_time timestamp,
	client_type_id int REFERENCES client_types(id),
	account_type_id int REFERENCES account_types(id)
);

insert into user(first_name, second_name, sur_name, email_addr, client_type_id, account_type_id) values('Hanumantarao', 'Mulpuru', 'Ganesh', 'monkey@bangalore.in',
	(select id from client_types where type = 'EXPERT'),
	(select id from account_types where name = 'For free'));
insert into user(first_name, second_name, sur_name, email_addr, client_type_id, account_type_id) values('Bhanagavan', 'Puuri', null, 'monkey2@bangalore.in',
	(select id from client_types where type = 'CANDIDATE'),
	(select id from account_types where name = 'For free'));


create table client_agreements(			-- коньдидат (и не только) должен быть согласен на все
	user_id int primary key,
	agreed bool default 0
);

insert into client_agreements(user_id, agreed) values(
	(select id from user where email_addr = 'monkey@bangalore.in'),
	true);

create table company(  					-- компания - обладатель api для добавления информации о кандидате
	id serial primary key,
	name varchar(256) not null,
	description varchar(512)
);

insert into company(name, description) values('Vector-2 Limited', 'Crime entites');

create table vacancy(
	user_id int REFERENCES user(id),
	company_id int REFERENCES company(id),
	title varchar(128) not null,
	vacancy_id int not null
);

insert into vacancy(user_id, company_id, title, vacancy_id) values(
	(select id from user where email_addr = 'monkey@bangalore.in'),
	(select id from company where name = 'Vector-2 Limited'),
	'Java Rocket Developer',
	42);

create table company_users(  					-- пользователи компании
	user_id int REFERENCES user(id),
	company_id int REFERENCES company(id),
	unique(user_id, company_id)
);

insert into company_users(user_id, company_id) values(
	(select id from user where email_addr = 'monkey@bangalore.in'),
	(select id from company where name = 'Vector-2 Limited'));


create table expertise(		-- что за эксперт - область, уровень
	id serial primary key,
	name varchar(32),
	keywords varchar(64) not null,
	description varchar(512) not null,
	level int not null
);

insert into expertise(name, keywords, description, level) values('Java Core', 'Java,Coding for food', 'Java development', -1);

create table expert_users(  					-- эксперты
	user_id int REFERENCES user(id),
	expertise_id int REFERENCES expertise(id),
	unique(user_id, expertise_id)
);

insert into expert_users(user_id, expertise_id) values(
	(select id from user where email_addr = 'monkey@bangalore.in'),
	(select id from expertise where name = 'Java Core'));

create table interview_application(  					-- заявка на интервью между коньдидатом и икспертом
	id serial primary key,
	candidate_id int REFERENCES user(id),
	expert_id int REFERENCES user(id),
	discipline_id int references expertise(id),
	date_time timestamp,
	date_of_interview timestamp,
	calendly_link varchar(256) not null,
	unique(candidate_id, expert_id)--,
	--index disc (discipline_id)
);

insert into interview_application(candidate_id, expert_id, discipline_id, calendly_link) values(
	(select id from user where email_addr = 'monkey2@bangalore.in'),
	(select id from user where email_addr = 'monkey@bangalore.in'),
	(select id from expertise where name = 'Java Core'),
	'http:calendly.com/path/to'
);

create table resolution_types(
	id serial primary key,
	name varchar(32) not null,
	description varchar(512) not null
);

insert into resolution_types(name, description) values('SUCCESS', 'Candidate has passed');
insert into resolution_types(name, description) values('FALI', 'Candidate has dropped');

create table apllication_solution(
	interview_application_id int REFERENCES interview_application(id),
	resolution_id int references resolution_types(id),
	report_id int not null,
	certificate_id int not null--,
	--INDEX ap_sol (interview_application_id, resolution_id)
);

insert into apllication_solution(interview_application_id, resolution_id, report_id, certificate_id) values(
	(select id from interview_application where candidate_id = (select id from user where email_addr = 'monkey2@bangalore.in') and expert_id = (select id from user where email_addr = 'monkey@bangalore.in')),
	(select id from resolution_types where name = 'FALI'),
	1,
	1
);


-- DROP TABLE IF EXISTS apllication_solution;
-- DROP TABLE IF EXISTS resolution_types;
-- DROP TABLE IF EXISTS interview_application;
-- DROP TABLE IF EXISTS expert_users;
-- DROP TABLE IF EXISTS expertise;
-- DROP TABLE IF EXISTS company_users;
-- DROP TABLE IF EXISTS vacancy;
-- DROP TABLE IF EXISTS company;
-- DROP TABLE IF EXISTS client_agreements;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS account_types;
-- DROP TABLE IF EXISTS client_types;

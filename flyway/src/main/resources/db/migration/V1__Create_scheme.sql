create table client_types(
	id integer primary key,
	type varchar(32) not null,	-- кандидат, эксперт, компания
	unique(type)
);

create table account_types(  			-- описание платного аккаунта
	id serial primary key,
	name varchar(64) not null,
	description varchar(512),
	period int not null,				-- дни
	cost numeric not null				-- рубли
);

create table user(
	id serial primary key,
	first_name varchar(64) not null,
	second_name varchar(64) not null,
	sur_name varchar(64),
	email_addr varchar(128),
	date_time timestamp,
	client_type_id int REFERENCES client_types(id),
	account_type_id int REFERENCES account_types(id)
);

create table client_agreements(			-- коньдидат (и не только) должен быть согласен на все
	user_id int primary key,
	agreed bool default false
);

create table company(  					-- компания - обладатель api для добавления информации о кандидате
	id serial primary key,
	name varchar(256) not null,
	description varchar(512)
);

create table vacancy(
	id serial primary key,
	company_id int REFERENCES company(id),
	title varchar(128) not null
);

create table company_users(  					-- пользователи компании
	user_id int REFERENCES user(id),
	company_id int REFERENCES company(id),
	unique(user_id, company_id)
);

create table company_vacancies(  					-- пользователи компании
	vacancy_id int REFERENCES vacancy(id),
	company_id int REFERENCES company(id),
	unique(vacancy_id, company_id)
);

create table user_vacancies(  					-- пользователи компании
	user_id int REFERENCES user(id),
	vacancy_id int REFERENCES vacancy(id),
	unique(user_id, vacancy_id)
);

create table expertise(		-- что за эксперт - область, уровень
	id serial primary key,
	name varchar(32),
	keywords varchar(64) not null,
	description varchar(512) not null,
	level int not null
);

create table user_expertise(
	user_id int REFERENCES user(id),
	expertise_id int REFERENCES expertise(id),
	unique(user_id, expertise_id)
);

create table interview_application(  					-- заявка на интервью между коньдидатом и икспертом
	id serial primary key,
	candidate_id int REFERENCES user(id),
	expert_id int REFERENCES user(id),
	discipline_id int references expertise(id),
	date_time timestamp not null,
	date_of_interview timestamp not null,
	calendly_link varchar(256) not null,
	unique(candidate_id, expert_id)--,
	--index disc (discipline_id)
);

insert into interview_application(candidate_id, expert_id, discipline_id, date_time, date_of_interview, calendly_link) values(
	(select id from user where email_addr = 'monkey2@bangalore.in'),
	(select id from user where email_addr = 'monkey@bangalore.in'),
	(select id from expertise where name = 'Java Core'),
	NOW(),
	'2019-12-01',
	'http:calendly.com/path/to'
);

create table resolution_types(
	id integer primary key,
	name varchar(32) not null,
	description varchar(512) not null
);

create table apllication_solution(
	interview_application_id int REFERENCES interview_application(id),
	resolution_id int references resolution_types(id),
	report_id int not null,
	certificate_id int not null--,
	--INDEX ap_sol (interview_application_id, resolution_id)
);

create table queries(
	id serial primary key,
	name varchar(64) not null,
	source varchar(1024) not null
);
insert into client_types(id, type) values(1, 'CANDIDATE');
insert into client_types(id, type) values(2, 'COMPANY');
insert into client_types(id, type) values(3, 'EXPERT');

insert into account_types(name, description, period, cost) values('For free', 'Try Trial', 30, 0);

insert into queries(name, source) values('selectUsersWithExpertisesByClientType', 'select u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type,
		act.id  as "accountType_id", act.name  as "accountType_name", act.cost as "accountType_cost", act.description  as "accountType_description", act.period  as "accountType_period"
	from user u
	JOIN client_types ct
		ON u.client_type_id = ct.id
	JOIN account_types act
		ON u.account_type_id = act.id
	where u.id in (:userIds) and ct.type = :clientType');
insert into queries(name, source) values('selectUsersWithExpertises', 'select u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type,
		act.id  as "accountType_id", act.name  as "accountType_name", act.cost as "accountType_cost", act.description  as "accountType_description", act.period  as "accountType_period"
	from user u
	JOIN client_types ct
		ON u.client_type_id = ct.id
	JOIN account_types act
		ON u.account_type_id = act.id
	where u.id in (:userIds)');

insert into queries(name, source) values('selectUserById', '  select u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, ca.agreed as "agreed",
		act.id  as "accountType_id", act.name  as "accountType_name", act.cost as "accountType_cost", act.description  as "accountType_description", act.period  as "accountType_period"
	from user u
	JOIN client_types ct
		ON u.client_type_id = ct.id
	JOIN account_types act
		ON u.account_type_id = act.id
	JOIN client_agreements ca
		ON u.id = ca.user_id
	where u.id = :userId');

insert into queries(name, source) values('userExpertiseTupleQuery', 'select * from user_expertise where (user_id, expertise_id) in (:tuples)');

insert into resolution_types(id, name, description) values(1, 'SUCCESS', 'Candidate has passed');
insert into resolution_types(id, name, description) values(2, 'FAIL', 'Candidate has dropped');
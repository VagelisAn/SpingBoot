-- liquibase formatted SQL

-- changeset vangelis:1
CREATE table users (
	id  SERIAL PRIMARY KEY NOT NULL,
    username varchar(100) not null,
    password varchar(100) not null,
    email varchar(100) not null,
    active BOOLEAN NOT NULL
);

CREATE table user_info (
	id  SERIAL PRIMARY KEY NOT NULL,
	user_id SERIAL,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    department varchar(100) not null,
    address varchar(100) not null,
    phone varchar(100) not null,
    CONSTRAINT fk_user
          FOREIGN KEY(user_id)
    	  REFERENCES users(id)
);

-- changeset vangelis:2

CREATE table user_role (
    id  SERIAL PRIMARY KEY NOT NULL,
    role_name varchar(100) not null
);

CREATE table user_privilege (
    id  SERIAL PRIMARY KEY NOT NULL,
    privilege_name varchar(100) not null
);

CREATE table user_to_role (
    id  SERIAL PRIMARY KEY NOT NULL,
    user_id SERIAL,
    role_id SERIAL,
    CONSTRAINT fk_user_role
              FOREIGN KEY(role_id)
              REFERENCES user_role(id),
    CONSTRAINT fk_user
              FOREIGN KEY(user_id)
              REFERENCES users(id)
);

CREATE table user_role_privilege (
    id  SERIAL PRIMARY KEY NOT NULL,
    role_id SERIAL,
    privilege_id SERIAL,
     CONSTRAINT fk_user_role
                  FOREIGN KEY(role_id)
                  REFERENCES user_role(id),
        CONSTRAINT fk_user_privilege
                  FOREIGN KEY(privilege_id)
                  REFERENCES user_privilege(id)
);

-- changeset vangelis:3
--insert into ds.public.users(id, username, password,email, active) values (1, 'user1', '{noop}user1','aaa', true);
--insert into ds.public.users (id, username, password,email, active) values (2, 'user2', '{noop}user2','sdss', true);
--insert into ds.public.users (id, username, password,email, active) values (3, 'admin', '{noop}admin', 'sasa', true);
--insert into ds.public.user_role(id, role_name) values (1, 'USER');
--insert into ds.public.user_role(id, role_name) values (2, 'ADMIN');
--insert into ds.public.user_privilege(id, privilege_name) values (1, 'canReadUser');
--insert into ds.public.user_privilege(id, privilege_name) values (2, 'canReadAdmin');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (1,1,'vagos','vag','aaa','mpotsarh','212561');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (2,2,'Hlias','Papas','a','tharkhs','21121');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (3,3,'Maria','Papa','s','Kuprou','515516');
--insert into ds.public.user_to_role(id, user_id, role_id) values (1, 1, 1);
--insert into ds.public.user_to_role(id, user_id, role_id) values (2, 2, 1);
--insert into ds.public.user_to_role(id, user_id, role_id) values (3, 3, 2);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (1, 1, 1);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (2, 2, 1);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (3, 2, 2);

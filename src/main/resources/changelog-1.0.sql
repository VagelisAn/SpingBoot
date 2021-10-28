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
    id  SERIAL NOT NULL,
    role_name varchar(100) not null
);

ALTER TABLE user_role ADD CONSTRAINT ROLE_PK PRIMARY KEY (id) ;

CREATE table user_privilege (
    id  SERIAL NOT NULL,
    privilege_name varchar(100) not null
);

ALTER TABLE user_privilege ADD CONSTRAINT PRIVILEGE_PK PRIMARY KEY (id) ;

CREATE table user_to_role (
    id  SERIAL PRIMARY KEY NOT NULL,
    user_id SERIAL,
    role_id SERIAL,
--    CONSTRAINT fk_user_role
--              FOREIGN KEY(role_id)
--              REFERENCES user_role(id),
    CONSTRAINT fk_user
              FOREIGN KEY(user_id)
              REFERENCES users(id)
);

ALTER TABLE user_to_role ADD CONSTRAINT USER_TO_ROLE_FK FOREIGN KEY ( role_id ) REFERENCES user_role ( id ) ;


CREATE table user_role_privilege (
    id  SERIAL PRIMARY KEY NOT NULL,
    role_id SERIAL,
    privilege_id SERIAL
--    CONSTRAINT fk_user_role
--                  FOREIGN KEY(role_id)
--                  REFERENCES user_role(id),
--        CONSTRAINT fk_user_privilege
--                  FOREIGN KEY(privilege_id)
--                  REFERENCES user_privilege(id)
);

ALTER TABLE user_role_privilege ADD CONSTRAINT USER_TO_ROLE_PRIVILEGE_FK FOREIGN KEY ( role_id ) REFERENCES user_role ( id ) ;

ALTER TABLE user_role_privilege ADD CONSTRAINT USER_TO_PRIVILEGE_FK FOREIGN KEY ( privilege_id ) REFERENCES user_role ( id ) ;

-- changeset vangelis:3
--insert into ds.public.users(id, username, password,email, active) values (1, 'user1', '{noop}user1','aaa', true);
--insert into ds.public.users (id, username, password,email, active) values (2, 'user2', '{noop}user2','sdss', true);
--insert into ds.public.users (id, username, password,email, active) values (3, 'admin', '{noop}admin', 'sasa', true);
insert into ds.public.user_role(id, role_name) values (1, 'ADMIN');
insert into ds.public.user_role(id, role_name) values (2, 'USER');
insert into ds.public.user_role(id, role_name) values (3, 'CREATOR');
insert into ds.public.user_role(id, role_name) values (4, 'EDITOR');
insert into ds.public.user_privilege(id, privilege_name) values (1, 'READ_AUTHORITY');
insert into ds.public.user_privilege(id, privilege_name) values (2, 'WRITE_AUTHORITY');
insert into ds.public.user_privilege(id, privilege_name) values (3, 'UPDATE_AUTHORITY');
insert into ds.public.user_privilege(id, privilege_name) values (4, 'DELETE_AUTHORITY');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (1,1,'vagos','vag','aaa','mpotsarh','212561');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (2,2,'Hlias','Papas','a','tharkhs','21121');
--INSERT INTO ds.public.user_info (id,user_id,first_name,last_name,department,address,phone) VALUES (3,3,'Maria','Papa','s','Kuprou','515516');
--insert into ds.public.user_to_role(id, user_id, role_id) values (1, 1, 1);
--insert into ds.public.user_to_role(id, user_id, role_id) values (2, 2, 1);
--insert into ds.public.user_to_role(id, user_id, role_id) values (3, 3, 2);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (1, 1, 1);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (2, 2, 1);
--insert into ds.public.user_role_privilege(id, role_id, privilege_id) values (3, 2, 2);

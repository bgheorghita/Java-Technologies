CREATE TABLE authorities (
     id BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(255),
     PRIMARY KEY (id)
);

CREATE TABLE roles (
   id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(255),
   PRIMARY KEY (id)
);

CREATE TABLE authorities_roles (
   authority_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   FOREIGN KEY (authority_id) REFERENCES authorities(id),
   FOREIGN KEY (role_id) REFERENCES roles(id)
);


CREATE TABLE users (
   id BIGINT NOT NULL AUTO_INCREMENT,
   username VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE users_roles (
     user_id BIGINT NOT NULL,
     role_id BIGINT NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users(id),
     FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE preferences (
     id BIGINT NOT NULL AUTO_INCREMENT,
     user_id BIGINT NOT NULL,
     content VARCHAR(255) NOT NULL,
     registration_number VARCHAR(255),
     FOREIGN KEY (user_id) REFERENCES users(id),
     PRIMARY KEY (id)
);

create table events (
    id bigint(20) not null auto_increment,
    name varchar(255) not null,
    primary key (id)
);

create table preferences_events(
   id bigint(20) not null auto_increment,
   preference_id bigint(20) not null,
   event_id bigint(20) not null,
   priority int not null,
   primary key(id),
   foreign key (preference_id) references preferences(id),
   foreign key (event_id) references events(id),
   UNIQUE (preference_id, event_id, priority)
);

insert into roles (name) values ('ROLE_TEACHER'), ('ROLE_ADMIN');
insert into users (username, password) values ('teacher', '$2a$12$a6rqpps4nFXLdjSgT7Xaiu8SrPKhCkgn2tL3N1LzkV4.7zXfXXSlG'), -- teacher
                                              ('admin', '$2a$12$ZtuJTa.PfratWBGBBRKmleMfn0GAcrkL1040QtR2Mgmr59f5DEa72'); -- admin
insert into users_roles (user_id, role_id) values (1, 1), (2, 2);

insert into events (name) values ('Math Class'), ('English Class'), ('Science Class'),
                                 ('History Class'), ('Art Class'), ('Lunch Break');
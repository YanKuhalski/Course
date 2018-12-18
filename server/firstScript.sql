create database if not exists course;
use course;

create table if not exists user(
id int auto_increment not null,
login varchar(20) not null unique,
password varchar(20) not null,
role varchar(20) not null,
email varchar(30) not null unique,
primary key(id)
);


create table if not exists test(
id int auto_increment not null,
name varchar(30) not null,
primary key(id)
);

create table if not exists question(
id int auto_increment not null,
test_id int not null,
text varchar(500) not null,
right_answer int not null,
primary key(id),
foreign key (test_id) references test (id) on delete cascade on update cascade
);

create table if not exists answer(
id int auto_increment not null,
text varchar(500) not null,
question_id int not null,
primary key(id),
foreign key (question_id) references question (id) on delete cascade on update cascade
);

create table if not exists result(
id int auto_increment not null,
user_id int not null,
test_id int not null,
result double(4,2) not null,
primary key(id),
foreign key (user_id) references user (id) on delete cascade on update cascade,
foreign key (test_id) references test (id) on delete no action on update cascade
);

create table if not exists interview(
id int auto_increment not null,
time varchar(15) not null,
date varchar(15) not null,
user_id int not null,
hr_id int not null,
primary key(id),
foreign key (user_id) references user (id) on delete cascade on update cascade,
foreign key (hr_id) references user (id) on delete cascade on update cascade
);


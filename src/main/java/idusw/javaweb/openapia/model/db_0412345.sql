/* DBA 권한 사용자(root 또는 mysql 데이터베이스에 권한있는 사용자)로 작업 */
drop database db_0412345;
create database db_0412345;
use db_0412345;

drop user 'user_0412345'@'%';
flush privileges;
select host, user from mysql.user; /* mysql db안에 존재하는 user table 대상 */

create user 'user_0412345'@'%' identified by 'cometrue';
grant all privileges on db_0412345.* to 'user_0412345'@'%';
flush privileges;
show grants for 'user_0412345'@'%';
show tables;

/* DDL : Data Definition Language, 데이터베이스, 테이블 등을 정의하는데 사용하는 언어 */
drop table if exists mb0412345;
create table mb0412345 (
    mid bigint auto_increment primary key,
    email varchar(30) not null,
    pw varchar(30) not null,
    full_name varchar(30) not null,
    zipcode varchar(5),
    reg_timestamp TIMESTAMP DEFAULT NOW()
);

/* DML : Data Manipulation Language - C.R.U.D */
/* create 는 insert 구문과 관련 높음  */
insert into mb0412345(full_name, email, pw)  values ('admin', 'admin@induk.ac.kr', 'cometrue');
insert into mb0412345(full_name, email, pw)  values ('dreams', 'dreams@induk.ac.kr', 'cometrue');
insert into mb0412345(full_name, email, pw)  values ('comso', 'comso@induk.ac.kr', 'cometrue');
insert into mb0412345(full_name, email, pw)  values ('passion', 'passion@induk.ac.kr', 'cometrue');
insert into mb0412345(full_name, email, pw)  values ('iduws', 'idusw@induk.ac.kr', 'cometrue');
/* read 는 select 구문과 관련 높음 */
select * from mb0412345;
select * from mb0412345 order by email asc;
select * from mb0412345 where email like '%@%';
update mb0412345 set zipcode = '01878' where mid = 1;
delete from mb0412345 where mid = 1;

/*-----------------------------------------------------------------------------*/
drop table if exists  prj0412345;

create table prj0412345 (
    pid bigint auto_increment primary key,
    project_name varchar(30) not null,  /* DTO : projectName, Form : project-name */
    project_leader varchar(30),
    project_description varchar(200),
    status varchar(15),
    client_company varchar(30),
    estimated_budget bigint,
    total_amount_spent bigint,
    estimated_project_duration bigint,
    project_image varchar(50),
    reg_timestamp TIMESTAMP DEFAULT NOW(),
    rev_timestamp TIMESTAMP DEFAULT NOW()
);
desc prj0412345;

/* C.R.U.D - create, read, update, delete 연산
   insert, select, update, delete 구문으로 처리 */
insert into prj0412345(project_name, project_leader, status) values('DCT-23th 프로젝트', 'admin', 'On Hold');
insert into prj0412345(project_name, project_leader, status) values('DCT-22th 프로젝트', 'dreams', 'Success');
insert into prj0412345(project_name, project_leader, status) values('DCT-21th 프로젝트', 'comso', 'Success');
insert into prj0412345(project_name, project_leader, status) values('DCT-20th 프로젝트', 'dreams', 'Success');
insert into prj0412345(project_name, project_leader, status) values('DCT-19th 프로젝트', 'passion', 'Success');

/* status : On Hold, Canceled, Success */
select * from prj0412345;
select rev_timestamp from prj0412345;
select * from prj0412345 where pid = 1;
select * from prj0412345 where project_name like '%DCT-2%';
update prj0412345 set rev_timestamp = '2023-12-14 07:00:00' where pid = 1;
delete from prj0412345 where pid = 1;

update prj0412345 set status = 'Success' where pid = 1;
delete from prj0412345 where pid = 1;

select now();
select VERSION();
SELECT @@GLOBAL.time_zone, @@SESSION.time_zone, @@system_time_zone;

drop table act0412345;

create table act0412345 (
    aid bigint auto_increment,
    title varchar(30) not null,
    description varchar(200),
    total_amount_spent bigint,
    mid bigint,
    reg_date TIMESTAMP DEFAULT NOW(),
    rev_date TIMESTAMP DEFAULT NOW(),
    primary key (aid),
    foreign key (mid) references mb0412345 (mid) on update cascade on delete set null
);
desc act0412345;
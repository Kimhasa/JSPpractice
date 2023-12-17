drop table t_prja202312345;

create table t_prja202312345 (
        pid bigint auto_increment primary key,
        project_name varchar(30) not null,
        project_description varchar(200),
        status varchar(15) not null,
        client_company varchar(5),
        project_leader varchar(30),
        estimated_budget bigint,
        total_amount_spent bigint,
        estimated_project_duration bigint
);
desc t_prja202312345;
/* C.R.U.D - insert, select, update, delete 구문으로 처리 */
insert into t_prja202312345(project_name, status) values('DCT-3 Project', 'On Hold'); /* On Hold, Canceled, Success */
select * from t_prja202312345 where pid = 1;
select * from t_prja202312345;
update t_prja202312345 set status = 'Success' where pid = 1;
delete from t_prja202312345 where pid = 1;
/*일반 테이블 구조*/
create table access_log
(
    create_time     datetime(6)  null,
    event_time      datetime(6)  null,
    id              bigint auto_increment
        primary key,
    employee_number varchar(255) null,
    user_name       varchar(255) null
);

/*인덱스*/
create index idx_event_time_employee_number_user_name on access_log(event_time, employee_number, user_name);

/*파티셔닝 구조*/
CREATE TABLE access_log
(
    id              BIGINT AUTO_INCREMENT,
    create_time     DATETIME(6)  NULL,
    event_time      DATETIME(6)  NOT NULL,
    employee_number VARCHAR(255) NULL,
    user_name       VARCHAR(255) NULL,
UNIQUE KEY uk_id_event_time (id, event_time)
) PARTITION BY RANGE (TO_DAYS(event_time))
(
PARTITION p202510 VALUES LESS THAN (TO_DAYS('2025-11-01')),
PARTITION p_default VALUES LESS THAN MAXVALUE
);
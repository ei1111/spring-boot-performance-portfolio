/*비관적락*/
create table test_lock(
    id    bigint auto_increment
primary key,
    stock int not null
);

/*낙관적락*/
create table test_lock(
    id      bigint auto_increment
        primary key,
    stock   int    not null,
    version bigint null
);

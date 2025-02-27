create table tb_user(
    id serial primary key,
    firstname varchar(255),
    lastname varchar(255),
    email varchar(255) unique,
    password varchar(255),
    created_at timestamp,
    updated_at timestamp
);

create table tb_authorities (
    id serial primary key,
    name varchar(255)
);

insert into tb_authorities (id, name) values (1, 'USER');

create table tb_user_authority (
    user_id serial not null,
    authority_id serial not null,

    constraint fk_usraut_userid foreign key (user_id) references tb_user(id),
    constraint fk_usraut_authorityid foreign key (authority_id) references tb_authorities(id),
    constraint pk_user_authority primary key(user_id, authority_id)
);

create table tb_subject(
    id serial primary key,
    name varchar(255),
    created_at timestamp,
    updated_at timestamp,
    user_id integer not null,

    constraint fk_sub_userid foreign key (user_id) references tb_user(id)
);

create table tb_topic(
    id serial primary key,
    name varchar(255),
    created_at timestamp,
    updated_at timestamp,
    subject_id integer not null,
    user_id integer not null,

    constraint fk_top_subjectid foreign key (subject_id) references tb_subject(id),
    constraint fk_top_userid foreign key (user_id) references tb_user(id)
);

create table tb_review (
    id serial primary key,
    days_to_review integer,
    review_in timestamp,
    created_at timestamp,
    subject_id integer not null,
    topic_id integer not null,
    user_id integer not null,

    constraint fk_rev_subjectid foreign key (subject_id) references tb_subject(id),
    constraint fk_rev_topicid foreign key (topic_id) references tb_topic(id),
    constraint fk_rev_userid foreign key (user_id) references tb_user(id)
);

create table tb_exercise(
    id serial primary key,
    correct_answers integer,
    wrong_answers integer,
    created_at timestamp,
    subject_id integer not null,
    topic_id integer not null,
    user_id integer not null,

    constraint fk_exc_subjectid foreign key (subject_id) references tb_subject(id),
    constraint fk_exc_topicid foreign key (topic_id) references tb_topic(id),
    constraint fk_exc_userid foreign key (user_id) references tb_user(id)
);

create table tb_session (
    id serial primary key,
    start_time timestamp,
    end_time timestamp,
    total_time timestamp,
    created_at timestamp,
    subject_id integer not null,
    topic_id integer not null,
    user_id integer not null,

    constraint fk_ses_subjectid foreign key (subject_id) references tb_subject(id),
    constraint fk_ses_topicid foreign key (topic_id) references tb_topic(id),
    constraint fk_ses_userid foreign key (user_id) references tb_user(id)
);
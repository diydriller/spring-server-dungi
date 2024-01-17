mysql --user=root --password=$MYSQL_ROOT_PASSWORD --execute \
"
create database if not exists spring_dungi;
use spring_dungi;


create table if not exists memo (
                                    memo_id bigint not null auto_increment,
                                    created_time datetime(6),
                                    modified_time datetime(6),
                                    delete_status varchar(255),
                                    memo_color varchar(255),
                                    memo_item varchar(255),
                                    room_id bigint,
                                    users_id bigint,
                                    x_position double precision,
                                    y_position double precision,
                                    primary key (memo_id)
) engine=InnoDB;

create table if not exists notice (
                                      notice_id bigint not null auto_increment,
                                      created_time datetime(6),
                                      modified_time datetime(6),
                                      delete_status varchar(255),
                                      notice_item varchar(255),
                                      room_id bigint,
                                      users_id bigint,
                                      primary key (notice_id)
) engine=InnoDB;

create table if not exists repeat_day (
                                          repeat_day_id bigint not null auto_increment,
                                          day integer not null,
                                          todo_id bigint,
                                          primary key (repeat_day_id)
) engine=InnoDB;

create table if not exists room (
                                    room_id bigint not null auto_increment,
                                    created_time datetime(6),
                                    modified_time datetime(6),
                                    color varchar(255),
                                    delete_status varchar(255),
                                    name varchar(255),
                                    primary key (room_id)
) engine=InnoDB;

create table if not exists todo (
                                    dtype varchar(31) not null,
                                    todo_id bigint not null auto_increment,
                                    created_time datetime(6),
                                    modified_time datetime(6),
                                    deadline datetime(6),
                                    delete_status varchar(255),
                                    room_id bigint,
                                    todo_item varchar(255),
                                    users_id bigint,
                                    todo_status varchar(255),
                                    primary key (todo_id)
) engine=InnoDB;

create table if not exists user_room (
                                         users_room_id bigint not null auto_increment,
                                         created_time datetime(6),
                                         modified_time datetime(6),
                                         delete_status varchar(255),
                                         users_id bigint,
                                         room_id bigint,
                                         primary key (users_room_id)
) engine=InnoDB;

create table if not exists users (
                                     users_id bigint not null auto_increment,
                                     created_time datetime(6),
                                     modified_time datetime(6),
                                     best_mate_count integer,
                                     delete_status varchar(255),
                                     email varchar(255),
                                     name varchar(255),
                                     nickname varchar(255),
                                     password varchar(255),
                                     phone_number varchar(255),
                                     profile_img varchar(255),
                                     provider varchar(255),
                                     primary key (users_id)
) engine=InnoDB;

create table if not exists user_vote_item (
                                              users_vote_item_id bigint not null auto_increment,
                                              created_time datetime(6),
                                              modified_time datetime(6),
                                              delete_status varchar(255),
                                              users_id bigint,
                                              vote_item_id bigint,
                                              primary key (users_vote_item_id)
) engine=InnoDB;

create table if not exists vote (
                                    vote_id bigint not null auto_increment,
                                    created_time datetime(6),
                                    modified_time datetime(6),
                                    delete_status varchar(255),
                                    vote_status varchar(255),
                                    room_id bigint,
                                    title varchar(255),
                                    users_id bigint,
                                    primary key (vote_id)
) engine=InnoDB;

create table if not exists vote_item (
                                         vote_item_id bigint not null auto_increment,
                                         created_time datetime(6),
                                         modified_time datetime(6),
                                         choice varchar(255),
                                         vote_id bigint,
                                         primary key (vote_item_id)
) engine=InnoDB;

create table if not exists notice_vote (
                             id bigint not null auto_increment,
                             content varchar(255),
                             created_time datetime(6),
                             modified_time datetime(6),
                             delete_status varchar(255),
                             notice_vote_id bigint,
                             room_id bigint,
                             type varchar(255),
                             users_id bigint,
                             primary key (id)
) engine=InnoDB;


alter table users
    add constraint user_idx unique (email);

alter table repeat_day
    add constraint repeat_day_fk_todo
        foreign key (todo_id)
            references todo (todo_id);

alter table user_room
    add constraint user_room_fk_room
        foreign key (room_id)
            references room (room_id);

alter table user_vote_item
    add constraint user_vote_item_fk_vote_item
        foreign key (vote_item_id)
            references vote_item (vote_item_id);

alter table vote_item
    add constraint vote_item_fk_vote
        foreign key (vote_id)
            references vote (vote_id);

alter table memo
    add constraint momo_fk_users
        foreign key (users_id)
            references users (users_id);

alter table notice
    add constraint notice_fk_users
        foreign key (users_id)
            references users (users_id);

alter table todo
    add constraint todo_fk_users
        foreign key (users_id)
            references users (users_id);

alter table todo
    add constraint todo_fk_room
        foreign key (room_id)
            references room (room_id);

alter table user_room
    add constraint user_room_fk_users
        foreign key (users_id)
            references users (users_id);

alter table user_vote_item
    add constraint user_vote_item_fk_users
        foreign key (users_id)
            references users (users_id);

alter table vote
    add constraint vote_fk_users
        foreign key (users_id)
            references users (users_id);

alter table todo
    add index todo_idx (created_time desc);

alter table notice_vote
    add index notice_vote_idx (created_time desc);

alter table user_vote_item
    add constraint user_vote_item_idx unique (users_id, vote_item_id);

change replication source to
   source_host='host.docker.internal',
   source_port=3300,
   source_user='root',
   source_password='$SOURCE_ROOT_PASSWORD',
   source_log_file='mysql-bin.000003',
   source_log_pos=157,
   get_source_public_key=1;

start replica;
"
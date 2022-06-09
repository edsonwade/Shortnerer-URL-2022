create database urlcompress;
drop table if exists url;

create table url(id integer not null auto_increment,
                     long_url varchar(255) not null,
                     short_url varchar(255) not null,
                     primary key (id)
)engine=InnoDB  default charset=utf8;

alter table url
add constraint UK_ru951hscq31qaip8joempxej9 unique (long_url);
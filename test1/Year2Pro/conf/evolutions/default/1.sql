# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table appointment (
  apm_number                    integer auto_increment not null,
  customer_email                varchar(255),
  artist_email                  varchar(255),
  date                          date,
  time                          varchar(255),
  description                   varchar(255),
  size                          varchar(255),
  placement                     varchar(255),
  colour                        varchar(255),
  paid                          boolean,
  constraint uq_appointment_customer_email unique (customer_email),
  constraint pk_appointment primary key (apm_number)
);

create table style (
  id                            bigint auto_increment not null,
  style_name                    varchar(255),
  constraint pk_style primary key (id)
);

create table tattoo (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  style_id                      bigint,
  artist_email                  varchar(255),
  constraint pk_tattoo primary key (id)
);

create table user (
  type                          varchar(31) not null,
  email                         varchar(255) not null,
  role                          varchar(255),
  name                          varchar(255),
  password                      varchar(255),
  phone_number                  varchar(255),
  aid                           integer,
  constraint uq_user_aid unique (aid),
  constraint pk_user primary key (email)
);

alter table appointment add constraint fk_appointment_customer_email foreign key (customer_email) references user (email) on delete restrict on update restrict;

alter table appointment add constraint fk_appointment_artist_email foreign key (artist_email) references user (email) on delete restrict on update restrict;
create index ix_appointment_artist_email on appointment (artist_email);

alter table tattoo add constraint fk_tattoo_style_id foreign key (style_id) references style (id) on delete restrict on update restrict;
create index ix_tattoo_style_id on tattoo (style_id);

alter table tattoo add constraint fk_tattoo_artist_email foreign key (artist_email) references user (email) on delete restrict on update restrict;
create index ix_tattoo_artist_email on tattoo (artist_email);

alter table user add constraint fk_user_aid foreign key (aid) references appointment (apm_number) on delete restrict on update restrict;


# --- !Downs

alter table appointment drop constraint if exists fk_appointment_customer_email;

alter table appointment drop constraint if exists fk_appointment_artist_email;
drop index if exists ix_appointment_artist_email;

alter table tattoo drop constraint if exists fk_tattoo_style_id;
drop index if exists ix_tattoo_style_id;

alter table tattoo drop constraint if exists fk_tattoo_artist_email;
drop index if exists ix_tattoo_artist_email;

alter table user drop constraint if exists fk_user_aid;

drop table if exists appointment;

drop table if exists style;

drop table if exists tattoo;

drop table if exists user;


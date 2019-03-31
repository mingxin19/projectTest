# --- Sample dataset

# --- !Ups
delete from user;
delete from appointment;
delete from style;

insert into user (type,email,name,password,role) values ( 'admin', 'admin@tattoo.com', 'Alice Admin', 'password', 'admin' );

insert into style (id,style_name) values ( 1, 'Traditional' );
insert into style (id,style_name) values ( 2, 'Trash-Polka' );
insert into style (id,style_name) values ( 3, 'Biomechanical' );
insert into style (id,style_name) values ( 4, 'Black Work' );
insert into style (id,style_name) values ( 5, 'Japanese' );
insert into style (id,style_name) values ( 6, 'Realism' );
insert into style (id,style_name) values ( 7, 'Tribal' );
insert into style (id,style_name) values ( 8, 'New School' );
insert into style (id,style_name) values ( 9, 'Neo Traditional' );
insert into style (id,style_name) values ( 10, 'Black and Grey' );
insert into style (id,style_name) values ( 11, 'Celtic' );
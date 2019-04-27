# --- Sample dataset

# --- !Ups



delete from tattoo;
delete from style;
delete from appointment;
delete from user;

insert into user (type,email,name,password,role) values ( 'admin', 'admin@tattoo.com', 'Alice Admin', 'password', 'admin' );
insert into user (type,email,name,password,role) values ( 'artist', 'andrew@artist.com', 'Andrew', 'password', 'artist' );


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

insert into tattoo (id,name,style_id,artist_email) values (1,'Wolf',10,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (2,'Wolf-sketch',10,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (3,'crow arm',10,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (4,'Chest-Crow-Tattoo',10,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (5,'Biomechanical Arm gears',3,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (6,'Raven Trash Polka',2,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (7,'wolf trash polka',2,'andrew@artist.com');
insert into tattoo (id,name,style_id,artist_email) values (8,'chain arm',10,'andrew@artist.com');
delete from usr_player;
delete from usr;
delete from player;

insert into usr(id, email, password) values
(1, 'test@test.ru', 'testpass');

insert into player(id, name) values
(2, 'testplayer');

insert into usr_player(player_id, usr_id) values
(2, 1);
insert into users (id, name) values (0, 'SYSTEM');
insert into users (name) values ('John');
insert into users (name) values ('Jane');

insert into activity (user_id, name, kcal_per_minute) values (0, 'Walking', 5.0);
insert into activity (user_id, name, kcal_per_minute) values (0, 'Running', 10.0);
insert into activity (user_id, name, kcal_per_minute) values (0, 'Cycling', 8.0);
insert into activity (user_id, name, kcal_per_minute) values (0, 'Swimming', 7.0);
insert into activity (user_id, name, kcal_per_minute) values (0, 'Weight Training', 3.0);

insert into exercises (user_id, activity_id, start_date, duration) values (1, 1, '2023-03-08 07:00:00', 30);
insert into exercises (user_id, activity_id, start_date, duration) values (2, 2, '2023-03-09 07:15:00', 45);
insert into exercises (user_id, activity_id, start_date, duration) values (1, 3, '2023-03-10 08:00:00', 60);


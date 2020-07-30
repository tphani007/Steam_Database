drop database steam;

create database steam;
use steam;

create table Administrator
(
    admin_id integer primary key,
    username varchar(20),
    pass varchar(20)
);

create table Customer
(
    cust_id integer primary key,
    username varchar(20),
    pass varchar(20),
    ad_id integer,
    constraint cust_ad_fk foreign key (ad_id) references Administrator (admin_id) 
);

create table Games
(
    game_id integer primary key,
    title varchar(20),
    genre varchar(20)
);

create table Owned_Games
(
    og_id integer primary key,
    c_id integer,
    g_id integer,
    constraint og_cust_fk foreign key (c_id) references Customer (cust_id),
    constraint og_game_fk foreign key (g_id) references Games (game_id)
);


insert into Administrator values
(
    1,
    "admin-01",
    "abc"
);

insert into Administrator values
(
    2,
    "admin-02",
    "xyz"
);

insert into Customer values
(
    1,
    "pro_gamer",
    "123",
    1
);

insert into Customer values
(
    2,
    "the_undefeated",
    "456",
    2
);

insert into Customer values
(
    3,
    "OG",
    "789",
    1
);

insert into Games values
(
    1,
    "Dota",
    "Moba"
);

insert into Games values
(
    2,
    "Half_Life",
    "Action"
);

insert into Games values
(
    3,
    "Counter-Strike",
    "FPS"
);

insert into Owned_Games values
(
    1,
    1,
    1
);

insert into Owned_Games values
(
    2,
    2,
    1
);

insert into Owned_Games values
(
    3,
    3,
    1
);

insert into Owned_Games values
(
    4,
    1,
    3
);

insert into Owned_Games values
(
    5,
    2,
    3
);

insert into Owned_Games values
(
    6,
    2,
    2
);
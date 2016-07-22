#$ strict off
drop index usrs_lck;
drop index usrs_author;
drop index game_enab;
drop table logons;
drop table rating;
drop table score;
drop table comments;
drop table game;
drop table usrs;
drop sequence usrid_seq;
drop sequence gameid_seq;
drop sequence comid_seq;
drop sequence logid_seq;

drop sequence halabalacervena; /*this statement fails :) bud ignores */

#$ strict on

create sequence usrid_seq start with 1 increment by 1;
create sequence gameid_seq start with 1 increment by 1;
create sequence comid_seq start with 1 increment by 1;
create sequence logid_seq start with 1 increment by 1;

create table usrs (
  usrid integer default null,
  uname varchar2(20) unique not null, 
  pwd varchar2(32) not null, /* an digest can be used to save safest password*/ 
  email varchar2(256) unique not null,
  dat date default current_timestamp, 
  isauthor varchar2(1) default 'N' check (isauthor in ('Y', 'N')), /* this flag told, the autor can add games into a studio */ 
  lck varchar2(1) default 'N' check (lck in ('Y', 'N')), /* this flag told, an account is locked by administrator of studio */
  primary key (usrid)
);

create index usrs_lck ON usrs (lck);
create index usrs_author ON usrs (isauthor);

create table logons (
  logid integer,
  usrid integer,
  ipv4 varchar(15) not null,
  ontime timestamp default current_timestamp,
  action varchar(1) default 'B' check (action in ('B', 'L', 'S')), /* Bad passw, Locked, Succes */
  primary key (logid),
  foreign key (usrid) references usrs(usrid) on delete set null
);

create table game (
  gameid INTEGER,
  gname varchar2(20) unique not null, /** Game name */
  author integer default NULL, /*it can be an id from another table (eg user with flag author)*/
  dat date default current_timestamp, /* can be an date + time */
  surl varchar(255) not null, /** an shorted url without hostname (FQDN)*/
  enab varchar2(1) default 'Y' check (enab in ('Y', 'N')), /* game enabled */
  RUNCLS VARCHAR2(255 BYTE) DEFAULT NULL,
  primary key(gameid),
  foreign key (author) references usrs(usrid) on delete set null
);

create index game_enab ON game (enab);

create table score (
  usrid integer,
  gameid integer,
  dat date default current_timestamp,
  score integer not null check (score >0),
  foreign key (usrid) references usrs(usrid) on delete set null,
  foreign key (gameid) references game(gameid) on delete set null
);

create table rating (
  usrid integer, 
  gameid integer, 
  dat date default current_timestamp,
  rating integer not null check (rating >0 and rating<6),
  primary key (usrid, gameid),
  foreign key (usrid) references usrs(usrid) on delete set null,
  foreign key (gameid) references game(gameid) on delete set null
);

create table comments (
  comid integer, 
  usrid integer,
  gameid integer,
  dat date default current_timestamp,
  msg varchar2(255) not null check (length(msg)>0),
  primary key (comid), 
  foreign key (usrid) references usrs(usrid) on delete set null,
  foreign key (gameid) references game(gameid) on delete set null
);  
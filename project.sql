create database hosteldb
use hosteldb
create table logintbl(id int ,name varchar(50), password varchar(50) not null)

create table studenttbl (
sid int not null primary key identity(1,1),
sfname varchar(50) not null,
slname varchar(50) not null,
sreg varchar(50) not null,
sdep varchar(50) not null,
scity varchar(50) not null,
sstate varchar(50) not null,
sphone varchar(50) not null,
rid int foreign key references roomtbl(rid)
)
alter table studenttbl
add images varbinary(max)
sp_help studenttbl

create table floortbl(
fid int not null primary key identity(1,1),fname varchar(50) not null,fcap int not null
)

create table roomtbl(
rid int not null primary key identity(1,1),rcap int not null,fid int null foreign key references floortbl(fid)
)

create table teamtbl (
tid int not null,
ttype varchar(50) not null primary key,
)

create table stafftbl(
    stid int not null primary key identity(1,1),
    stfname varchar(50) not null,
    stlname varchar(50) not null,
    stsalary varchar(50) not null,
    stcity varchar(50) not null,
    ststate varchar(50) not null,
    stemail varchar(50) not null,
    stphone varchar(50) not null,
    ttype varchar(50) foreign key references teamtbl(ttype)
    )

    alter table stafftbl
    add stimage image
    sp_help stafftbl

insert into teamtbl(tid,ttype) values(6,'Security Guard')

insert into stafftbl(stfname,stlname,stsalary,stcity,ststate,stemail,stphone,ttype) values
('fa','df','1000','dfsdf','sgf','asfj@gmail.com','43234224','Security Guard')

insert into logintbl (id,name,password) values(1,'admin','admin123')

insert into floortbl(fname,fcap) values('Basement',5)
,('First',6),('Second',5),('Third',9);

insert into roomtbl(rcap,fid) values(3,4),(2,4),(3,4),(2,4),(3,4),(3,4),(3,4),(3,4),(3,4),(2,3)





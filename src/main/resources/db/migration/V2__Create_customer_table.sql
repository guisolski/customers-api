create table CUSTOMER (
    ID int not null AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(100) not null,
    EMAIL varchar(50) not null,
    CPF  varchar(14) not null,
    GENDER enum('Masculino','Feminino') not null,
    BIRTHDATE timestamp,
    CREATEAT timestamp,
    UPDATEAT timestamp
);
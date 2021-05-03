create table ADDRESS (
    ID int not null AUTO_INCREMENT PRIMARY KEY,
    CUSTOMERID int not null,
    STATE varchar(100) not null,
    CITY varchar(100) not null,
    NEIGHBORHOOD varchar(100) not null,
    STREET varchar(100) not null,
    ADDITIONALINFORMATION varchar(100),
    ZIPCODE varchar(9) not null,
    NUMBER int not null,
    MAIN boolean not null,
    CREATEAT timestamp not null,
    UPDATEAT timestamp not null
);
ALTER TABLE ADDRESS
ADD CONSTRAINT ADDRESS_CUSTOMERID_FK
FOREIGN KEY (CUSTOMERID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE;

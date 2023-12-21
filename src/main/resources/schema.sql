DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
                         USER_ID INT AUTO_INCREMENT PRIMARY KEY,
                         NAME VARCHAR2(50) NOT NULL,
                         ADDRESS VARCHAR2(30),
                         PHONE_NUMBER VARCHAR2(10),
                         EMAIL VARCHAR2(30)
);

CREATE INDEX USER_ID on USERS(USER_ID);

DROP TABLE IF EXISTS SHOP;
CREATE TABLE SHOP (
                      SHOP_ID VARCHAR2(50) PRIMARY KEY,
                      SHOP_NAME VARCHAR2(50) NOT NULL,
                      ADDRESS VARCHAR2(30),
                      USER_ID VARCHAR2(50),
                      foreign key (USER_ID) references USERS(USER_ID)
);

CREATE INDEX SHOP_USER_ID on SHOP(USER_ID);
CREATE INDEX SHOP_ID on SHOP(SHOP_ID);


DROP TABLE IF EXISTS SHIFT;
CREATE TABLE SHIFT (
                      SHIFT_ID VARCHAR2(50) PRIMARY KEY,
                      SHIFT_DESC VARCHAR2(50),
                      SHIFT_DURATION NUMBER,
                      USER_ID VARCHAR2(50),
                      SHOP_ID VARCHAR2(50),
                      START_TIME timestamp(6) not null,
                      END_TIME timestamp(6) not null,
                      foreign key (USER_ID) references USERS(USER_ID),
                      foreign key (SHOP_ID) references SHOP(SHOP_ID)
);

CREATE INDEX SHIFT_USER_ID on SHIFT(USER_ID);
CREATE INDEX SHIFT_SHOP_ID on SHIFT(SHOP_ID);
CREATE INDEX SHIFT_ID on SHIFT(SHIFT_ID);
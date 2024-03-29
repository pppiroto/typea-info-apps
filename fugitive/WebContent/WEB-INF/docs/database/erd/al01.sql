CREATE TABLE ALBUM_USER
(
    USER_ID                        VARCHAR2(20) NOT NULL,
    PASSWORD                       VARCHAR2(10)
)
/
ALTER TABLE ALBUM_USER
    ADD(PRIMARY KEY (USER_ID) USING INDEX)
/
CREATE TABLE ALBUM_USER_ROLE
(
    USER_ID                        VARCHAR2(20) NOT NULL,
    SEQ                            INT NOT NULL,
    ROLE                           VARCHAR2(20)
)
/
ALTER TABLE ALBUM_USER_ROLE
    ADD(PRIMARY KEY (USER_ID, SEQ) USING INDEX)
/
CREATE TABLE ALBUM_USER_PATH
(
    USER_ID                        VARCHAR2(20) NOT NULL,
    SEQ                            INT NOT NULL,
    PATH                           VARCHAR2(255)
)
/
ALTER TABLE ALBUM_USER_PATH
    ADD(PRIMARY KEY (USER_ID, SEQ) USING INDEX)
/
ALTER TABLE ALBUM_USER_ROLE
    ADD(FOREIGN KEY(USER_ID) REFERENCES ALBUM_USER (USER_ID))
/
ALTER TABLE ALBUM_USER_PATH
    ADD(FOREIGN KEY(USER_ID) REFERENCES ALBUM_USER (USER_ID))
/

CREATE TABLE BOOK
(
    ASIN                           VARCHAR2(20) NOT NULL,
    TITLE                          VARCHAR2(200),
    PAGE_URL                       VARCHAR2(500),
    SMALL_IMAGE                    VARCHAR2(100),
    MEDIUM_IMAGE                   VARCHAR2(100),
    LARGE_IMAGE                    VARCHAR2(100),
    KEYWORD_1                      VARCHAR2(100),
    KEYWORD_2                      VARCHAR2(100),
    KEYWORD_3                      VARCHAR2(100),
    KEYWORD_4                      VARCHAR2(100),
    KEYWORD_5                      VARCHAR2(100),
    CREATE_DATE                    TIMESTAMP,
    CREATE_USER                    VARCHAR2(20),
    CREATE_HOST                    VARCHAR2(20)
)
/
ALTER TABLE BOOK
    ADD(PRIMARY KEY (ASIN) USING INDEX)
/
CREATE INDEX IDX_KEYWORD_1
    ON BOOK (KEYWORD_1)
/
CREATE INDEX IDX_KEYWORD_2
    ON BOOK (KEYWORD_2)
/
CREATE INDEX IDX_KEYWORD_3
    ON BOOK (KEYWORD_3)
/
CREATE INDEX IDX_KEYWORD_4
    ON BOOK (KEYWORD_4)
/
CREATE INDEX IDX_KEYWORD_5
    ON BOOK (KEYWORD_5)
/
CREATE TABLE BOOK_COMMENT
(
    ASIN                           VARCHAR2(20) NOT NULL,
    SEQ                            NUMERIC(4,0) NOT NULL,
    BOOK_COMMENT                   VARCHAR2(400),
    POS_X                          NUMERIC(4,0),
    POS_Y                          NUMERIC(4,0),
    CREATE_DATE                    TIMESTAMP,
    CREATE_USER                    VARCHAR2(20),
    CREATE_HOST                    VARCHAR2(20)
)
/
ALTER TABLE BOOK_COMMENT
    ADD(PRIMARY KEY (ASIN, SEQ) USING INDEX)
/

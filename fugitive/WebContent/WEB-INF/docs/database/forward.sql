CREATE TABLE BOOK
(
    ASIN                           VARCHAR2(20) NOT NULL,
    TITLE                          VARCHAR2(200),
    PAGE_URL                       VARCHAR2(500),
    SMALL_IMG                      VARCHAR2(100),
    MEDIUM_IMG                     VARCHAR2(100),
    LARGE_IMG                      VARCHAR2(100)
)
/
ALTER TABLE BOOK
    ADD(PRIMARY KEY (ASIN) USING INDEX)
/

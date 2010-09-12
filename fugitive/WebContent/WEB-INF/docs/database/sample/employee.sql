CREATE TABLE HR.EMPLOYEES
(
    EMPLOYEE_ID                    NUMBER(6,0) NOT NULL,
    FIRST_NAME                     VARCHAR2(20),
    LAST_NAME                      VARCHAR2(25) NOT NULL,
    EMAIL                          VARCHAR2(25) NOT NULL,
    PHONE_NUMBER                   VARCHAR2(20),
    HIRE_DATE                      DATE NOT NULL,
    JOB_ID                         VARCHAR2(10) NOT NULL,
    SALARY                         NUMBER(8,2),
    COMMISSION_PCT                 NUMBER(2,2),
    MANAGER_ID                     NUMBER(6,0),
    DEPARTMENT_ID                  NUMBER(4,0),
    CONSTRAINT EMP_EMP_ID_PK PRIMARY KEY (EMPLOYEE_ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
        TABLESPACE USERS
        STORAGE(INITIAL 64K MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT)
        LOGGING,
    CONSTRAINT EMP_DEPT_FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES HR.DEPARTMENTS (DEPARTMENT_ID),
    CONSTRAINT EMP_EMAIL_NN CHECK ("EMAIL" IS NOT NULL),
    CONSTRAINT EMP_EMAIL_UK UNIQUE (EMAIL) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
        TABLESPACE USERS
        STORAGE(INITIAL 64K MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT)
        LOGGING,
    CONSTRAINT EMP_HIRE_DATE_NN CHECK ("HIRE_DATE" IS NOT NULL),
    CONSTRAINT EMP_JOB_FK FOREIGN KEY (JOB_ID) REFERENCES HR.JOBS (JOB_ID),
    CONSTRAINT EMP_JOB_NN CHECK ("JOB_ID" IS NOT NULL),
    CONSTRAINT EMP_LAST_NAME_NN CHECK ("LAST_NAME" IS NOT NULL),
    CONSTRAINT EMP_MANAGER_FK FOREIGN KEY (MANAGER_ID) REFERENCES HR.EMPLOYEES (EMPLOYEE_ID),
    CONSTRAINT EMP_SALARY_MIN CHECK (salary > 0)
)
PCTFREE 10
MAXTRANS 255
TABLESPACE USERS
STORAGE(INITIAL 64K MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT)
NOCACHE
LOGGING
/
COMMENT ON TABLE HR.EMPLOYEES IS 'employees table. Contains 107 rows. References with departments,
jobs, job_history tables. Contains a self reference.'
/
COMMENT ON COLUMN HR.EMPLOYEES.EMPLOYEE_ID IS 'Primary key of employees table.'
/
COMMENT ON COLUMN HR.EMPLOYEES.FIRST_NAME IS 'First name of the employee. A not null column.'
/
COMMENT ON COLUMN HR.EMPLOYEES.LAST_NAME IS 'Last name of the employee. A not null column.'
/
COMMENT ON COLUMN HR.EMPLOYEES.EMAIL IS 'Email id of the employee'
/
COMMENT ON COLUMN HR.EMPLOYEES.PHONE_NUMBER IS 'Phone number of the employee; includes country code and area code'
/
COMMENT ON COLUMN HR.EMPLOYEES.HIRE_DATE IS 'Date when the employee started on this job. A not null column.'
/
COMMENT ON COLUMN HR.EMPLOYEES.JOB_ID IS 'Current job of the employee; foreign key to job_id column of the
jobs table. A not null column.'
/
COMMENT ON COLUMN HR.EMPLOYEES.SALARY IS 'Monthly salary of the employee. Must be greater
than zero (enforced by constraint emp_salary_min)'
/
COMMENT ON COLUMN HR.EMPLOYEES.COMMISSION_PCT IS 'Commission percentage of the employee; Only employees in sales
department elgible for commission percentage'
/
COMMENT ON COLUMN HR.EMPLOYEES.MANAGER_ID IS 'Manager id of the employee; has same domain as manager_id in
departments table. Foreign key to employee_id column of employees table.
(useful for reflexive joins and CONNECT BY query)'
/
COMMENT ON COLUMN HR.EMPLOYEES.DEPARTMENT_ID IS 'Department id where employee works; foreign key to department_id
column of the departments table'
/

CREATE TABLE HR.DEPARTMENTS
(
    DEPARTMENT_ID                  NUMBER(4,0) NOT NULL,
    DEPARTMENT_NAME                VARCHAR2(30) NOT NULL,
    MANAGER_ID                     NUMBER(6,0),
    LOCATION_ID                    NUMBER(4,0),
    CONSTRAINT DEPT_ID_PK PRIMARY KEY (DEPARTMENT_ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
        TABLESPACE USERS
        STORAGE(INITIAL 64K MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT)
        LOGGING,
    CONSTRAINT DEPT_LOC_FK FOREIGN KEY (LOCATION_ID) REFERENCES HR.LOCATIONS (LOCATION_ID),
    CONSTRAINT DEPT_MGR_FK FOREIGN KEY (MANAGER_ID) REFERENCES HR.EMPLOYEES (EMPLOYEE_ID),
    CONSTRAINT DEPT_NAME_NN CHECK ("DEPARTMENT_NAME" IS NOT NULL)
)
PCTFREE 10
MAXTRANS 255
TABLESPACE USERS
STORAGE(INITIAL 64K MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT)
NOCACHE
LOGGING
/
COMMENT ON TABLE HR.DEPARTMENTS IS 'Departments table that shows details of departments where employees
work. Contains 27 rows; references with locations, employees, and job_history tables.'
/
COMMENT ON COLUMN HR.DEPARTMENTS.DEPARTMENT_ID IS 'Primary key column of departments table.'
/
COMMENT ON COLUMN HR.DEPARTMENTS.DEPARTMENT_NAME IS 'A not null column that shows name of a department. Administration,
Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public
Relations, Sales, Finance, and Accounting. '
/
COMMENT ON COLUMN HR.DEPARTMENTS.MANAGER_ID IS 'Manager_id of a department. Foreign key to employee_id column of employees table. The manager_id column of the employee table references this column.'
/
COMMENT ON COLUMN HR.DEPARTMENTS.LOCATION_ID IS 'Location id where a department is located. Foreign key to location_id column of locations table.'
/

CREATE TABLE IF NOT EXISTS Course (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    END_DATE DATE,
    NAME VARCHAR(255),
    START_DATE DATE,
    PRIMARY KEY (ID)
) ;

CREATE TABLE IF NOT EXISTS Learning_Log (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    DATE DATE NOT NULL,
    TASK_CATEGORY ENUM ('PRACTICING','RESEARCHING','WATCHING_VIDEOS'),
    TASK_DESCRIPTION VARCHAR(255),
    TIME_SPENT DATETIME(6) NOT NULL,
    STUDENT_ID BIGINT,
    PRIMARY KEY (ID)
) ;

CREATE TABLE IF NOT EXISTS Registration (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    COURSE_ID BIGINT,
    STUDENT_ID BIGINT,
    PRIMARY KEY (ID)
) ;

CREATE TABLE IF NOT EXISTS Student (
    ID BIGINT NOT NULL,
    ADDRESS VARCHAR(255),
    DATE_OF_BIRTH DATE NOT NULL,
    EMAIL VARCHAR(255),
    FIRST_NAME VARCHAR(50),
    LAST_NAME VARCHAR(50),
    PHONE_NUMBER VARCHAR(50),
    PRIMARY KEY (ID)
) ;

ALTER TABLE Course ADD CONSTRAINT COURSE_UNIQUE UNIQUE (NAME);


ALTER TABLE Student ADD CONSTRAINT STUDENT_UNIQUE UNIQUE (EMAIL);
ALTER TABLE Learning_Log ADD CONSTRAINT LEARNING_LOG_FK FOREIGN KEY (STUDENT_ID) REFERENCES Student (ID);
ALTER TABLE Registration ADD CONSTRAINT REGISTRATION_COURSE_FK FOREIGN KEY (COURSE_ID) REFERENCES Course (ID);
ALTER TABLE Registration ADD CONSTRAINT REGISTRATION_STUDENT_FK FOREIGN KEY (STUDENT_ID) REFERENCES Student (ID);
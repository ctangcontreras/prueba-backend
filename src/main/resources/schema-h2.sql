CREATE TABLE USUARIO
        (
        /* id NUMBER(10) NOT NULL, */
        id VARCHAR2(200) NOT NULL,
        name VARCHAR2(200) NOT NULL,
        email VARCHAR2(200)  NULL,
        active NUMBER(1) NOT NULL,
        password VARCHAR2(200) NULL,
        date_create date   NULL,
        date_modify date   NULL,
        date_last_login date   NULL,  
        PRIMARY KEY(id)
        );

CREATE TABLE TELEFONO
        (
        /* id NUMBER(10) NOT NULL, */
        id VARCHAR2(200) NOT NULL,
        id_user VARCHAR2(200) NOT NULL,
        num_tel VARCHAR2(200) NOT NULL,
        city_code VARCHAR2(200)  NULL,
        country_code VARCHAR2(200)  NULL,
        active NUMBER(1) NOT NULL,     
        date_create date   NULL,
        date_modify date   NULL,
        date_last_login date NULL,        
        PRIMARY KEY(id),
        FOREIGN KEY(id_user) REFERENCES USUARIO(id)
        );
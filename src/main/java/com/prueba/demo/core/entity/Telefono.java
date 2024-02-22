package com.prueba.demo.core.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TELEFONO")
@Setter
@Getter
public class Telefono {
    
     @Column(name = "id")
     @Id
     private String id;

     @Column(name = "id_user")
     private String idUser;

     @Column(name = "num_tel")
     private String number;

     @Column(name = "city_code")
     private String cityCode;

     @Column(name = "country_code")
     private String countryCode;

     @Column(name = "active")
     private Integer active;

     @Column(name = "date_create")
     private LocalDateTime dateCreate;    

     @Column(name = "date_modify")
     private LocalDateTime dateModify;    

     @Column(name = "date_last_login")
     private LocalDateTime dateLastLogin;
     
}

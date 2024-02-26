package com.prueba.demo.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoResponse {
    
    private String id;
    private String created;
    private Integer active;
    private String modified;
    private String last_login;
    private String token;
    
    public DtoResponse(String id, String created, Integer active, String modified,
    String last_login, String token) {
        this.id = id;
        this.created = created;
        this.active = active;
        this.modified = modified;
        this.last_login = last_login;
        this.token = token;
    }

    public DtoResponse(String id, String last_login, String token) {
        this.id = id;
        this.last_login = last_login;
        this.token = token;
    }
    

    

    
}

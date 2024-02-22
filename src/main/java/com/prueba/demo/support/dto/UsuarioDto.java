package com.prueba.demo.support.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prueba.demo.validation.PasswordConstraint;


@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {
    
    private String id;
    private String name;

    @Email(message = "El campo 'email' debe tener un formato de correo electrónico válido")
    private String email;

    @PasswordConstraint
    private String password;

    private Integer active;
    private LocalDateTime dateCreate;
    private LocalDateTime dateModify;
    private LocalDateTime dateLastLogin;
    private List<TelefonoDto> phones;

    @Setter
    @Getter
    public static class TelefonoDto {
        private String id;
        private String idUser;
        private String number;
        private String cityCode;
        private String countryCode;
        private Integer active;
        private LocalDateTime dateCreate;
        private LocalDateTime dateModify;
        private LocalDateTime dateLastLogin;
    }
}

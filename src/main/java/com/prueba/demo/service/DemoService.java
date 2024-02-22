package com.prueba.demo.service;
 

import com.prueba.demo.support.dto.Respuesta;
 

public interface DemoService {

	Respuesta<?> getListarUsuario() throws Exception;
}

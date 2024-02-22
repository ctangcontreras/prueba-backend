package com.prueba.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.demo.repository.UsuarioRepository;
import com.prueba.demo.service.DemoService;
import com.prueba.demo.support.dto.Respuesta;
 
 

@Service
public class DemoServiceImpl implements DemoService {
	private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);


	@Autowired
	UsuarioRepository usuarioRepository;
 
	@Override
	public Respuesta<?> getListarUsuario() throws Exception {
		 
		return new Respuesta<>(true, usuarioRepository.findAll());
	}

 
	 
}
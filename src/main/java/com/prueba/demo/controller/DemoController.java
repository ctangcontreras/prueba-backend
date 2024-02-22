package com.prueba.demo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.demo.core.entity.Usuario;
import com.prueba.demo.service.DemoService;
import com.prueba.demo.support.dto.UsuarioDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/demo")
@Api(value = "HelloWorld Resource", description = "shows hello world")
public class DemoController {

	private static final Logger log = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private DemoService demoService;
	
	@ApiOperation(value = "listar Usuario")
	@RequestMapping(value = "/getListarUsuario", method = RequestMethod.GET)
	public ResponseEntity<Object> getListaParametro() {
		
		try {
			return ResponseEntity.ok(demoService.getListarUsuario());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.ok(e);
		}
	}

	@ApiOperation(value = "Registra y actualiza usuario")
	@RequestMapping(value = "/addUsuario", method = RequestMethod.POST)
	public ResponseEntity<Object> addUsuario(@Valid @RequestBody UsuarioDto dto) {
		
		try {
			if (dto.getId()!=null && !dto.getId().equals("")) {
				return ResponseEntity.ok(demoService.updateUsuario(dto));
			} else {
				return ResponseEntity.ok(demoService.addUsuario(dto));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.ok(e);
		}
	}

	@ApiOperation(value = "Iniciar sesion")
	@RequestMapping(value = "/iniciarSesion", method = RequestMethod.POST)
	public ResponseEntity<Object> iniciarSesion(@RequestBody UsuarioDto dto) {
		
		try {
			return ResponseEntity.ok(demoService.iniciarSesion(dto));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.ok(e);
		}
	}

 
}

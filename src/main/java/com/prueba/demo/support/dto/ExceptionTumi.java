package com.prueba.demo.support.dto;

import java.util.Map;

public class ExceptionTumi extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6785430262042605181L;
	private boolean success;
	private String message;
	private Map<String, Object> parametros;

	public ExceptionTumi(boolean success) {
		this.success = success;
	}

	public ExceptionTumi(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
 

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

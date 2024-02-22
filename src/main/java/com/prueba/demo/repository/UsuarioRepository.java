package com.prueba.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.demo.core.entity.Usuario;


 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
package com.prueba.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.prueba.demo.core.entity.Usuario;
import com.prueba.demo.repository.TelefonoRepository;
import com.prueba.demo.repository.UsuarioRepository;
import com.prueba.demo.service.impl.DemoServiceImpl;
import com.prueba.demo.support.dto.UsuarioDto;
import com.prueba.demo.support.dto.Respuesta;
import com.prueba.demo.support.dto.JwtUtil;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TelefonoRepository telefonoRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private DemoServiceImpl demoService;

    @Before
    public void setUp() {
       MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetListarUsuario() throws Exception {

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(new Usuario()));

        Respuesta<?> respuesta = demoService.getListarUsuario();
        
        assertThat(respuesta.isSuccess(), is(true));
        assertNotNull(respuesta.getDato());
    }

    @Test
    public void testAddUsuario() throws Exception {

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("registrar@gmail.com");
        usuarioDto.setName("Nombre prueba");
        usuarioDto.setPassword("PassowrdPass1!");
        usuarioDto.setPhones(new ArrayList<>());

        when(usuarioRepository.findByEmail(usuarioDto.getEmail())).thenReturn(Arrays.asList());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());

        when(jwtUtil.createToken(anyString())).thenReturn("");

        Respuesta<?> respuesta = demoService.addUsuario(usuarioDto);

        
        assertThat(respuesta.isSuccess(), is(true));
        assertNotNull(respuesta.getMessage(), is("Se registró correctamente"));
    }


    @Test
    public void testUpdateUsuario() throws Exception {

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId("1");
        usuarioExistente.setEmail("existente@gmail.com");
        usuarioExistente.setName("Nombre Existente");
        usuarioExistente.setPassword("PasswordExistente1!");
        
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId("1");
        usuarioDto.setEmail("actualizar@gmail.com");
        usuarioDto.setName("Nombre prueba");
        usuarioDto.setPassword("PasswordPass2!");
        usuarioDto.setPhones(new ArrayList<>());

        when(usuarioRepository.findByEmail(usuarioDto.getEmail())).thenReturn(Arrays.asList(usuarioExistente));
        when(usuarioRepository.findById(usuarioDto.getId())).thenReturn(usuarioExistente);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());
        when(jwtUtil.createToken(anyString())).thenReturn("");

        Respuesta<?> respuesta = demoService.updateUsuario(usuarioDto);

        assertThat(respuesta.isSuccess(), is(true));
        assertNotNull(respuesta.getMessage(), is("Se actualizó correctamente"));
    }

}
package com.prueba.demo.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.demo.core.entity.Telefono;
import com.prueba.demo.core.entity.Usuario;
import com.prueba.demo.repository.TelefonoRepository;
import com.prueba.demo.repository.UsuarioRepository;
import com.prueba.demo.service.DemoService;
import com.prueba.demo.support.dto.DtoResponse;
import com.prueba.demo.support.dto.JwtUtil;
import com.prueba.demo.support.dto.Respuesta;
import com.prueba.demo.support.dto.UserDetail;
import com.prueba.demo.support.dto.UsuarioDto;
import com.prueba.demo.support.dto.UsuarioDto.TelefonoDto;

@Service
public class DemoServiceImpl implements DemoService {
	private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TelefonoRepository telefonoRepository;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public Respuesta<?> getListarUsuario() throws Exception {

		
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		List<Telefono> listaTelefono= new ArrayList<>();

		List<UsuarioDto> listaUsuarioDto = new ArrayList<>();
		UsuarioDto usuarioDto = new UsuarioDto();

		List<UsuarioDto.TelefonoDto> listaTelefonoDto = new ArrayList<>();
		UsuarioDto.TelefonoDto telefonoDto = new TelefonoDto();

		for (Usuario usuario : listaUsuarios) {
			listaTelefono = new ArrayList<>();
			usuarioDto = new UsuarioDto();
			listaTelefonoDto = new ArrayList<>();
			
			usuarioDto.setId(usuario.getId());
			usuarioDto.setName(usuario.getName());
			usuarioDto.setEmail(usuario.getEmail());
			usuarioDto.setPassword(usuario.getPassword());
			usuarioDto.setActive(usuario.getActive());
			usuarioDto.setDateCreate(usuario.getDateCreate());
			usuarioDto.setDateModify(usuario.getDateModify());
			usuarioDto.setDateLastLogin(usuario.getDateLastLogin());
			listaTelefono = telefonoRepository.findByIdUser(usuario.getId());
			for (Telefono telefono : listaTelefono) {
				telefonoDto = new TelefonoDto();
				telefonoDto.setId(telefono.getId());
				telefonoDto.setIdUser(telefono.getIdUser());
				telefonoDto.setNumber(telefono.getNumber());
				telefonoDto.setCityCode(telefono.getCityCode());
				telefonoDto.setCountryCode(telefono.getCountryCode());
				telefonoDto.setActive(telefono.getActive());
				telefonoDto.setDateCreate(telefono.getDateCreate());
				telefonoDto.setDateModify(telefono.getDateModify());
				telefonoDto.setDateLastLogin(telefono.getDateLastLogin());
				listaTelefonoDto.add(telefonoDto);
			}
			usuarioDto.setPhones(listaTelefonoDto);
			listaUsuarioDto.add(usuarioDto);
		}
		
		return new Respuesta<>(true, listaUsuarioDto);
	}

	@Override
	public Respuesta<?> addUsuario(UsuarioDto dto) throws Exception {

		List<Usuario> validarCorreo = usuarioRepository.findByEmail(dto.getEmail());
		if (validarCorreo!=null && !validarCorreo.isEmpty()) {
			return new Respuesta<>(false, null, "El correo ya fue registrado");
		}


		UUID uuid = null;
		
		uuid = UUID.randomUUID();
        String idUsuarioGenerado = uuid.toString();
		dto.setId(idUsuarioGenerado);
		
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setName(dto.getName());
		usuario.setEmail(dto.getEmail());
		usuario.setPassword(dto.getPassword());
		usuario.setActive(dto.getActive());
		usuario.setDateCreate(LocalDateTime.now());
		usuario.setDateModify(dto.getDateModify());
		usuario.setDateLastLogin(LocalDateTime.now());
		Usuario usuarioSave = usuarioRepository.save(usuario);

		Telefono telefono = new Telefono();
		String idTelefonoGenerado = "";

		for (TelefonoDto phone : dto.getPhones()) {
			telefono = new Telefono();
			idTelefonoGenerado = "";

			uuid = UUID.randomUUID();
        	idTelefonoGenerado = uuid.toString();
			phone.setId(idTelefonoGenerado);
			
			telefono.setId(phone.getId());
			telefono.setIdUser(usuarioSave.getId());
			telefono.setNumber(phone.getNumber());
			telefono.setCityCode(phone.getCityCode());
			telefono.setCountryCode(phone.getCountryCode());
			telefono.setActive(phone.getActive());
			telefono.setDateCreate(usuarioSave.getDateCreate());
			telefono.setDateModify(usuarioSave.getDateModify());
			telefono.setDateLastLogin(usuarioSave.getDateLastLogin());
			telefonoRepository.save(telefono);
		}

		UserDetail detail = new UserDetail();
		detail.setEmail(dto.getEmail());
		detail.setName(dto.getName());
		detail.setId(usuarioSave.getId());

		String token = jwtUtil.createToken(usuarioSave.getId());

		DtoResponse response = new DtoResponse(usuarioSave.getId(), formatLocalDate(usuarioSave.getDateCreate()),
				usuarioSave.getActive(), usuarioSave.getDateModify()!=null ?(formatLocalDate(usuarioSave.getDateModify())) : null, formatLocalDate(usuarioSave.getDateLastLogin()), token);

		return new Respuesta<>(true, response, "Se registró correctamente");
	}

	@Override
	public Respuesta<?> updateUsuario(UsuarioDto dto) throws Exception {


		List<Usuario> validarCorreo = usuarioRepository.findByEmail(dto.getEmail());
		Boolean mismoUsuario = false;
		for (Usuario usuario : validarCorreo) {
			if (dto.getId().equals(usuario.getId())) {
				mismoUsuario = true;
			}
		}
		if (!mismoUsuario && validarCorreo!=null && !validarCorreo.isEmpty()) {
			return new Respuesta<>(false, null, "El correo ya registrado");
		}


		UUID uuid = null;

		Usuario usuario = usuarioRepository.findById(dto.getId());
		usuario.setId(dto.getId());
		usuario.setName(dto.getName());
		usuario.setEmail(dto.getEmail());
		usuario.setPassword(dto.getPassword());
		usuario.setActive(dto.getActive());
		usuario.setDateModify(LocalDateTime.now());
		Usuario usuarioSave = usuarioRepository.save(usuario);

		Telefono telefono = new Telefono();
		String idTelefonoGenerado = "";

		for (TelefonoDto phone : dto.getPhones()) {
			telefono = new Telefono();
			idTelefonoGenerado = "";
			if (phone.getId()==null || phone.getId().equals("")) {
				uuid = UUID.randomUUID();
        		idTelefonoGenerado = uuid.toString();
				phone.setId(idTelefonoGenerado);
			}
			telefono.setId(phone.getId());
			telefono.setIdUser(usuarioSave.getId());
			telefono.setNumber(phone.getNumber());
			telefono.setCityCode(phone.getCityCode());
			telefono.setCountryCode(phone.getCountryCode());
			telefono.setActive(phone.getActive());
			telefono.setDateCreate(usuarioSave.getDateCreate());
			telefono.setDateModify(usuarioSave.getDateModify());
			telefono.setDateLastLogin(usuarioSave.getDateLastLogin());
			telefonoRepository.save(telefono);
		}

		String token = jwtUtil.createToken(usuarioSave.getId());

		DtoResponse response = new DtoResponse(usuarioSave.getId(), formatLocalDate(usuarioSave.getDateCreate()),
				usuarioSave.getActive(), formatLocalDate(usuarioSave.getDateModify()), formatLocalDate(usuarioSave.getDateLastLogin()), token);

		return new Respuesta<>(true, response, "Se actualizó correctamente");
	}

	@Override
	public Respuesta<?> iniciarSesion(UsuarioDto dto) throws Exception {

		Usuario usuario = usuarioRepository.findById(dto.getId());
		usuario.setDateLastLogin(LocalDateTime.now());
		Usuario usuarioUpdate = usuarioRepository.save(usuario);

		DtoResponse response = new DtoResponse(usuarioUpdate.getId(), formatLocalDate(usuarioUpdate.getDateLastLogin()));
		return new Respuesta<>(true, response, "Se inició sesión correctamente");
	}

	private static String formatLocalDate(LocalDateTime localDateTime) {
		
		String fechaFormateada = "";

		if (localDateTime!=null) {
			fechaFormateada = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN,Locale.GERMANY).format(localDateTime);
		}

        return fechaFormateada;
    }

}
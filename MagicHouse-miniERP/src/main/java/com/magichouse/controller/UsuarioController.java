package com.magichouse.controller;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.magichouse.dto.RegistroDTO;
import com.magichouse.dto.UsuarioDTO;
import com.magichouse.model.Usuario;
import com.magichouse.service.interfaces.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final IUsuarioService service;
	private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() throws Exception {
        List<Usuario> lista = service.findAll();
        Type listType = new TypeToken<List<UsuarioDTO>>(){}.getType();
        List<UsuarioDTO> usuarios = modelMapper.map(lista, listType);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Short id) throws Exception {
    	Usuario obj = service.findById(id);
        UsuarioDTO dto = modelMapper.map(obj, UsuarioDTO.class);
        return ResponseEntity.ok(dto);
    }

    private final org.springframework.security.crypto.password.PasswordEncoder bcrypt;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RegistroDTO dto) throws Exception {
        // Prevent duplicate usernames
        if (service.findOneByNombreUsuario(dto.getNombreUsuario()) != null) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).build();
        }

    	Usuario usuario = modelMapper.map(dto, Usuario.class);
        // Fix ModelMapper mismatch and encode password
        usuario.setContraseniaHash(bcrypt.encode(dto.getContrasenia()));
        // Set required fields
        usuario.setFechaHoraRegistro(java.time.LocalDateTime.now());
        usuario.setFechaHoraModificacion(java.time.LocalDateTime.now());
        usuario.setEstaActivo(true);
        
        java.util.List<com.magichouse.model.Rol> roles = new java.util.ArrayList<>();
        com.magichouse.model.Rol rol = new com.magichouse.model.Rol();
        rol.setIdRol((short)1);
        roles.add(rol);
        usuario.setRoles(roles);
        
        Usuario obj = service.save(usuario);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdUsuario()).toUri();
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO dto, @PathVariable("id") Short id) throws Exception {
    	Usuario usuario = modelMapper.map(dto, Usuario.class);
        
        Usuario existing = service.findById(id);
        existing.setNombreUsuario(usuario.getNombreUsuario());
        // keep other existing fields
        
        Usuario obj = service.update(existing, id);
        UsuarioDTO respuestaDTO = modelMapper.map(obj, UsuarioDTO.class);
        return ResponseEntity.ok(respuestaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Short id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

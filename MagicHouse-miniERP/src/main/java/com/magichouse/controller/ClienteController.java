package com.magichouse.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.magichouse.dto.ClienteDTO;
import com.magichouse.model.Cliente;
import com.magichouse.model.EstadoCliente;
import com.magichouse.service.interfaces.IClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() throws Exception {
        List<ClienteDTO> list = service.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ClienteDTO dto) throws Exception {
        Cliente obj = modelMapper.map(dto, Cliente.class);
        
        // Asignar el estado al cliente basado en el DTO
        if (dto.getIdEstadoCliente() != null) {
            EstadoCliente estado = new EstadoCliente();
            estado.setIdEstadoCliente(dto.getIdEstadoCliente());
            obj.setEstadosCliente(estado);
        }
        
        obj.setFechaHoraRegistro(LocalDateTime.now());
        obj.setFechaHoraModificacion(LocalDateTime.now());
        
        // Si no envía dniDevuelto, asumimos false por defecto.
        if (obj.getDniDevuelto() == null) {
            obj.setDniDevuelto(false);
        }

        Cliente objSaved = service.save(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objSaved.getIdCliente()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("id") Integer id, @RequestBody ClienteDTO dto) throws Exception {
        Cliente obj = modelMapper.map(dto, Cliente.class);
        obj.setIdCliente(id);
        
        if (dto.getIdEstadoCliente() != null) {
            EstadoCliente estado = new EstadoCliente();
            estado.setIdEstadoCliente(dto.getIdEstadoCliente());
            obj.setEstadosCliente(estado);
        }
        
        obj.setFechaHoraModificacion(LocalDateTime.now());
        
        // Mantener la fecha de registro original en el service update (generalmente se hace en el impl)
        // pero lo enviamos aquí
        Cliente objUpdated = service.update(obj, id);
        return ResponseEntity.ok(modelMapper.map(objUpdated, ClienteDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

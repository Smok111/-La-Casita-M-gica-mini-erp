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

import com.magichouse.dto.NegocioDTO;
import com.magichouse.model.Negocio;
import com.magichouse.service.interfaces.INegocioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/negocios")
@RequiredArgsConstructor
public class NegocioController {

    private final INegocioService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<NegocioDTO>> findAll() throws Exception {
        List<Negocio> lista = service.findAll();
        Type listType = new TypeToken<List<NegocioDTO>>() {
        }.getType();
        List<NegocioDTO> negocios = modelMapper.map(lista, listType);
        return ResponseEntity.ok(negocios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NegocioDTO> findById(@PathVariable("id") Short id) throws Exception {
        Negocio obj = service.findById(id);
        NegocioDTO dto = modelMapper.map(obj, NegocioDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NegocioDTO dto) throws Exception {
        Negocio negocio = modelMapper.map(dto, Negocio.class);
        Negocio obj = service.save(negocio);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getIdNegocio()).toUri();
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NegocioDTO> update(@RequestBody NegocioDTO dto, @PathVariable("id") Short id)
            throws Exception {
        Negocio negocio = modelMapper.map(dto, Negocio.class);
        Negocio obj = service.update(negocio, id);
        NegocioDTO respuestaDTO = modelMapper.map(obj, NegocioDTO.class);
        return ResponseEntity.ok(respuestaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Short id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

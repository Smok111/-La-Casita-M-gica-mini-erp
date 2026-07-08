package com.magichouse.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.magichouse.dto.CategoriaDTO;
import com.magichouse.model.Categoria;
import com.magichouse.service.interfaces.ICategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final ICategoriaService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() throws Exception {
        List<CategoriaDTO> list = service.findAll().stream().map(cat -> modelMapper.map(cat, CategoriaDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CategoriaDTO dto) throws Exception {
        Categoria obj = modelMapper.map(dto, Categoria.class);
        obj = service.save(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCategoria()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable("id") Integer id, @RequestBody CategoriaDTO dto) throws Exception {
        Categoria obj = modelMapper.map(dto, Categoria.class);
        obj.setIdCategoria(id);
        Categoria objUpdated = service.update(obj, id);
        return ResponseEntity.ok(modelMapper.map(objUpdated, CategoriaDTO.class));
    }
}

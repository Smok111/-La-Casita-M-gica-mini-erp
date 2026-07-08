package com.magichouse.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

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

import com.magichouse.dto.ModeloDisfrazDTO;
import com.magichouse.model.ModeloDisfraz;
import com.magichouse.model.Categoria;
import com.magichouse.service.interfaces.IModeloDisfrazService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/modelos-disfraz")
@RequiredArgsConstructor
public class ModeloDisfrazController {

    private final IModeloDisfrazService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ModeloDisfrazDTO>> findAll() throws Exception {
        List<ModeloDisfrazDTO> list = service.findAll().stream().map(md -> {
            ModeloDisfrazDTO dto = modelMapper.map(md, ModeloDisfrazDTO.class);
            dto.setIdCategoria(md.getCategoria().getIdCategoria());
            dto.setNombreCategoria(md.getCategoria().getNombreCategoria());
            return dto;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ModeloDisfrazDTO dto) throws Exception {
        ModeloDisfraz obj = modelMapper.map(dto, ModeloDisfraz.class);
        
        // Manual mapping for Categoria reference
        Categoria cat = new Categoria();
        cat.setIdCategoria(dto.getIdCategoria());
        obj.setCategoria(cat);
        
        // Set creation date
        obj.setFechaHoraRegistro(LocalDateTime.now());
        
        ModeloDisfraz objSaved = service.save(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objSaved.getIdModeloDisfraz()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloDisfrazDTO> update(@PathVariable("id") Integer id, @RequestBody ModeloDisfrazDTO dto) throws Exception {
        ModeloDisfraz obj = modelMapper.map(dto, ModeloDisfraz.class);
        obj.setIdModeloDisfraz(id);
        
        Categoria cat = new Categoria();
        cat.setIdCategoria(dto.getIdCategoria());
        obj.setCategoria(cat);

        ModeloDisfraz objUpdated = service.update(obj, id);
        ModeloDisfrazDTO resDto = modelMapper.map(objUpdated, ModeloDisfrazDTO.class);
        resDto.setIdCategoria(objUpdated.getCategoria().getIdCategoria());
        resDto.setNombreCategoria(objUpdated.getCategoria().getNombreCategoria());
        return ResponseEntity.ok(resDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

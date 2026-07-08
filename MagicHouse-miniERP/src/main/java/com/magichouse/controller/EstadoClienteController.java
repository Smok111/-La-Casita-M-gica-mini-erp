package com.magichouse.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magichouse.dto.EstadoClienteDTO;
import com.magichouse.service.interfaces.IEstadoClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estados-cliente")
@RequiredArgsConstructor
public class EstadoClienteController {

    private final IEstadoClienteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EstadoClienteDTO>> findAll() throws Exception {
        List<EstadoClienteDTO> list = service.findAll().stream()
                .map(estado -> modelMapper.map(estado, EstadoClienteDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}

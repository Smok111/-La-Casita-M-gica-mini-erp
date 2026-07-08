package com.magichouse.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magichouse.dto.MetodoPagoDTO;
import com.magichouse.service.interfaces.IMetodoPagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final IMetodoPagoService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> findAll() throws Exception {
        List<MetodoPagoDTO> list = service.findAll().stream()
                .map(mp -> modelMapper.map(mp, MetodoPagoDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}

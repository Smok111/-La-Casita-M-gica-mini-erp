package com.magichouse.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistroDTO {

	@NotBlank
    @Size(max = 60)
    private final String nombreUsuario;
    
    @NotBlank
    @Size(min = 8, max = 60)
    private final String contrasenia;
    
    private List<Short> rolesIds;
}

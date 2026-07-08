package com.magichouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    private Short idCategoria;
    
    @NotBlank
    @Size(max = 30)
    private String nombreCategoria;
    
    @Size(max = 250)
    private String descripcion;
    
    @NotNull
    private Boolean estaActiva;
}

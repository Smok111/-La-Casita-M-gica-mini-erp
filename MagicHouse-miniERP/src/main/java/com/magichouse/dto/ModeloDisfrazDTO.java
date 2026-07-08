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
public class ModeloDisfrazDTO {

    private Integer idModeloDisfraz;
    
    @NotNull
    private Short idCategoria; // Referencia a Categoria
    
    @NotBlank
    @Size(max = 80)
    private String nombreModelo;
    
    @Size(max = 250)
    private String descripcion;
    
    @NotNull
    private Boolean estaActivo;
    
    private Integer stockDisponible;
    
    private java.math.BigDecimal precioAlquiler;
    
    // Campo utilitario para mostrar el nombre de la categoría en el frontend
    private String nombreCategoria;
}

package com.magichouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoClienteDTO {
    private Short idEstadoCliente;
    private String nombreEstadoCliente;
    private String descripcion;
    private Boolean estaActivo;
}

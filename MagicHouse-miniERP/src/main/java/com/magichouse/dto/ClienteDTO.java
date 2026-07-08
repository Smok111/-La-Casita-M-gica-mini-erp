package com.magichouse.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Integer idCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String dni;
    private String numCelular;
    private Boolean dniDevuelto;
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime fechaHoraModificacion;
    private String direccion;
    
    // Foreign key properties
    private Short idEstadoCliente;
    private String nombreEstadoCliente;
}

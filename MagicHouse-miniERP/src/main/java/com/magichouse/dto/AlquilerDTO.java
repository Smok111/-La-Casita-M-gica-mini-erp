package com.magichouse.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerDTO {
    private Long idAlquiler;
    private Integer idCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String nombreMetodoPago;
    private LocalDate fechaAlquiler;
    private LocalDate fechaPactada;
    private LocalDate fechaEntrega;
    private BigDecimal total;
    private Boolean estaActivo;
}

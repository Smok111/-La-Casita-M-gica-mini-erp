package com.magichouse.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerRequestDTO {
    private Integer idCliente;
    private Short idMetodoPago;
    private LocalDate fechaPactada;
    private BigDecimal total;
    private List<DetalleAlquilerRequestDTO> detalles;
}

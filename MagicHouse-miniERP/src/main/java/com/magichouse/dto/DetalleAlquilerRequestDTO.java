package com.magichouse.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleAlquilerRequestDTO {
    private Integer idModeloDisfraz;
    private BigDecimal precioUnitarioAlquiler;
    private BigDecimal descuentoPorMayor;
}

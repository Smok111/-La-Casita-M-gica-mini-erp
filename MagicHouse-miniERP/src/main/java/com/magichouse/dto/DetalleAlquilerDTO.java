package com.magichouse.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleAlquilerDTO {
    private Integer idDetalleAlquiler;
    private Integer idItemDisfraz;
    private String codigoSKU;
    private String nombreModeloDisfraz;
    private BigDecimal precioUnitarioAlquiler;
    private BigDecimal descuentoPorMayor;
    private BigDecimal cargoDanio;
}

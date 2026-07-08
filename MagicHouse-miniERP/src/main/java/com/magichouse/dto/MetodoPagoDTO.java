package com.magichouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagoDTO {
    private Short idMetodoPago;
    private String nombreMetodoPago;
    private Boolean estaActivo;
}

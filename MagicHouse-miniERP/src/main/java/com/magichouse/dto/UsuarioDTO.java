package com.magichouse.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Short idUsuario;
    private String nombreUsuario;
    private Boolean estaActivo;
}

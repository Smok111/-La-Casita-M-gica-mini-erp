package com.magichouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	@NotBlank
    @Size(max = 60)
    private String nombreUsuario;
	
    @NotBlank
    @Size(min = 8, max = 60)
    private String contrasenia;
}

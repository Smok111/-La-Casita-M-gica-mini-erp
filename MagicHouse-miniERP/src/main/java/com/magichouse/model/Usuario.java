package com.magichouse.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_usuario")
	private Short idUsuario;
	
	@Column(nullable = false, length = 60)
	private String nombreUsuario;
	

	
	@Column(nullable = false, length = 60)
	private String contraseniaHash; // BCrypt -> 60
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime fechaHoraRegistro;
	
	@Column(nullable = false)
	private LocalDateTime fechaHoraModificacion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_usuario",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles;
}

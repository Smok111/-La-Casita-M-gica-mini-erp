package com.magichouse.model;

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
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_rol")
	private Short idRol;
	
	@Column(nullable = false, length = 30)
	private String nombreRol;
	
	@Column(nullable = true, length = 150)
	private String descripcion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_opciones_menu",
			joinColumns = @JoinColumn(name = "id_rol"),
			inverseJoinColumns = @JoinColumn(name = "id_opcion_menu")
	)
	private List<OpcionMenu> opcionesMenu;
}

package com.magichouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "opciones_menu")
public class OpcionMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_opcion_menu")
	private Short idOpcionMenu;
	
	@Column(nullable = false, unique = true, length = 50)
	private String nombreOpcionMenu;
	
	@Column(nullable = false, unique = true)
	private Short numOrden;
	
	@Column(nullable = false, length = 2000)
	private String urlIcono; // Recomendación de facto -> 2000
	
	@Column(nullable = false, length = 2000)
	private String rutaFrontend;
	
	@Column(nullable = false)
	private Boolean estaActiva;
}

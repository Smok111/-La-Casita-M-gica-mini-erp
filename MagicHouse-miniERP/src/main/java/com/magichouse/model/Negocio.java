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
@Table(name = "negocios")
public class Negocio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_negocio")
	private Short idNegocio;
	
	@Column(nullable = false, length = 100)
	private String nombreNegocio;
	
	@Column(nullable = true, length = 11)
	private String rucNegocio; // SUNAT -> 11
	
	@Column(nullable = true, length = 9)
	private String numCelular; // Nacionalmente -> 9
	
	@Column(nullable = true, length = 250)
	private String direccion;
}

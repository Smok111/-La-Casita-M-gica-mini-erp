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
@Table(name = "conceptos_kardex")
public class ConceptoKardex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_concepto_kardex")
	private Short idConceptoKardex;
	
	@Column(nullable = false, unique = true, length = 30)
	private String nombreConcepto;
	
	@Column(nullable = false, length = 7)
	private String movimiento; // Regla de negocio [Entrada/Salida] -> 7
	
	@Column(nullable = true, length = 250)
	private String descripcion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
}

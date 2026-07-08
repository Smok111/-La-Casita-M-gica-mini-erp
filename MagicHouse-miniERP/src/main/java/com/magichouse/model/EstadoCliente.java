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
@Table(name = "estados_cliente")
public class EstadoCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_estado_cliente")
	private Short idEstadoCliente;
	
	@Column(nullable = false, unique = true, length = 20)
	private String nombreEstadoCliente;
	
	@Column(nullable = true, unique = true, length = 250)
	private String descripcion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
}

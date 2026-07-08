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
@Table(name = "estados_reserva")
public class EstadoReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_estado_reserva")
	private Short EstadoReserva;
	
	@Column(nullable = false, unique = true, length = 20)
	private String nombreEstadoReserva;
	
	@Column(nullable = true, length = 250)
	private String descripcion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
}

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
@Table(name = "estados_item_disfraz")
public class EstadoItemDisfraz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_estado_item_disfraz")
	private Short idEstadoItemDisfraz;
	
	@Column(nullable = false, unique = true, length = 20)
	private String nombreEstadoItemDisfraz;
	
	@Column(nullable = true, length = 250)
	private String descripcion;
	
	@Column(nullable = false)
	private Boolean estaActivo;
}

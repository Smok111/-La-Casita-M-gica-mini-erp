package com.magichouse.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "detalles_alquilere")
public class DetalleAlquiler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_detalle_alquiler")
	private Integer idDetalleAlquiler;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_ALQUILER__ITEM_DISFRAZ"))
	private ItemDisfraz itemDisfraz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_alquiler", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_ALQUILER__ALQUILER"))
	private Alquiler alquiler;
	
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal precioUnitarioAlquiler; // Regla de negocio -> [1-999], Ley peruana N° 30381 -> 2
	
	@Column(nullable = false, precision = 4, scale = 2)
	private BigDecimal descuentoPorMayor; // Regla de negocio -> [1-99], Ley peruana N° 30381 -> 2
	
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal cargoDanio; // Regla de negocio -> [1-999], Ley peruana N° 30381 -> 2
}

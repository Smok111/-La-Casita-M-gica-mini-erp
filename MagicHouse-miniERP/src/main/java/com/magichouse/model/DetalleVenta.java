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
@Table(name = "detalles_venta")
public class DetalleVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_detalle_venta")
	private Long idDetalleVenta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venta", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_VENTA__VENTA"))
	private Venta venta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_VENTA__ITEM_DISFRAZ"))
	private ItemDisfraz itemDisfraz;
	
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal precioUnitarioVenta; // Regla de negocio -> [1-9999], Ley peruana N° 30381 -> 2
}

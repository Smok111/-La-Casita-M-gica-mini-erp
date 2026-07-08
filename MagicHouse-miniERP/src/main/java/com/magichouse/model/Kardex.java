package com.magichouse.model;

import java.time.LocalDateTime;

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
@Table(name = "kardex")
public class Kardex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_kardex")
	private Long idKardex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_KARDEX__USUARIO"))
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_concepto_kardex", nullable = false, foreignKey = @ForeignKey(name = "FK_KARDEX__CONCEPTO_KARDEX"))
	private ConceptoKardex conceptoKardex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_KARDEX__ITEM_DISFRAZ"))
	private ItemDisfraz itemDisfraz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venta", nullable = true, foreignKey = @ForeignKey(name = "FK_KARDEX__VENTA"))
	private Venta venta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_alquiler", nullable = true, foreignKey = @ForeignKey(name = "FK_KARDEX__ALQUILER"))
	private Alquiler alquiler;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private Integer stockResultante;
	
	@Column(nullable = false, unique = true)
	private LocalDateTime fechaHoraMovimiento;
}

package com.magichouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "alquileres")
public class Alquiler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_alquiler")
	private Long idAlquiler;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_ALQUILER__USUARIO"))
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "FK_ALQUILER__CLIENTE"))
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_metodo_pago", nullable = false, foreignKey = @ForeignKey(name = "FK_ALQUILER__METODO_PAGO"))
	private MetodoPago metodoPago;
	
	@Column(nullable = false, updatable = true)
	private LocalDate fechaAlquiler;
	
	@Column(nullable = false)
	private LocalDate fechaPactada;
	
	@Column(nullable = true)
	private LocalDate fechaEntrega;
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal total; // Regla de negocio -> [1-99999], Ley peruana N° 30381 -> 2
	
	private Boolean estaActivo;
}

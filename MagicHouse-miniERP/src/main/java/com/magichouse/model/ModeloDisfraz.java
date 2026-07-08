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
@Table(name = "modelos_disfraz")
public class ModeloDisfraz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_modelo_disfraz")
	private Integer idModeloDisfraz;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria", nullable = false, foreignKey = @ForeignKey(name = "FK_MODELOS_DISFRAZ__CATEGORIAS"))
	private Categoria categoria;
	
	@Column(nullable = false, unique = true, length = 80)
	private String nombreModelo;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime fechaHoraRegistro;
	
	@Column(nullable = true, length = 250)
	private String descripcion;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 1")
	private Integer stockDisponible = 1;
	
	@Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
	private java.math.BigDecimal precioAlquiler = java.math.BigDecimal.ZERO;
	
	@Column(nullable = false)
	private Boolean estaActivo;
}

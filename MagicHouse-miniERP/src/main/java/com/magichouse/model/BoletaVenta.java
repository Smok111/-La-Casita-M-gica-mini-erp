package com.magichouse.model;

import java.math.BigDecimal;
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
@Table(name = "boletas_venta")
public class BoletaVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_boleta_venta")
	private Long idBoletaVenta;
	
    @Column(nullable = false, updatable = false)
    private String numBoleta;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_negocio", nullable = false, foreignKey = @ForeignKey(name = "FK_BOLETA_VENTA__NEGOCIO"))
	private Negocio negocio;
	
	@Column(nullable = false, updatable = false, length = 100)
	private String nombreNegocio;
	
	@Column(nullable = false, updatable = false, length = 11)
	private String rucNegocio;
	
	@Column(nullable = false, updatable = false, length = 250)
	private String direccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venta", nullable = false, foreignKey = @ForeignKey(name = "FK_BOLETA_VENTA__VENTA"))
	private Venta venta;
	
	@Column(nullable = false, updatable = false, length = 60)
	private String nombresCliente;
	
	@Column(nullable = false, updatable = false, length = 60)
	private String apellidosCliente;
	
	@Column(nullable = false, updatable = false, length = 30)
	private String nombreMetodoPago;
    
	@Column(nullable = false, updatable = false, precision = 7, scale = 2)
	private BigDecimal total; // Regla de negocio -> [1-99999], Ley peruana N° 30381 -> 2
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaHoraEmision;
	
	@Column(nullable = false)
    private Boolean estaActiva;
}

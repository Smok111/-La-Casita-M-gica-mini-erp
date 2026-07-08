package com.magichouse.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_cliente")
	private Integer idCliente;
	
	@Column(nullable = false, length = 60)
	private String nombresCliente;
	
	@Column(nullable = false, length = 60)
	private String apellidosCliente;
	
	@Column(nullable = false, unique = true, length = 8)
	private String dni; // Perú -> 8
	
	@Column(nullable = true, length = 9)
	private String numCelular; // Nacionalmente -> 9
	
	@Column(nullable = false)
	private Boolean dniDevuelto;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime fechaHoraRegistro;
	
	@Column(nullable = false)
	private LocalDateTime fechaHoraModificacion;
	
	@Column(nullable = true, length = 250)
	private String direccion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado_cliente", nullable = false, foreignKey = @ForeignKey(name = "FK_CLIENTES__ESTADOS_CLIENTE"))
	private EstadoCliente estadosCliente;
}

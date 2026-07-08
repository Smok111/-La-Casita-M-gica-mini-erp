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
@Table(name = "reservas_disfraz")
public class ReservaDisfraz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_reserva_disfraz")
	private Long idReservaDisfraz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVA_DISFRAZ__USUARIO"))
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVA_DISFRAZ__CLIENTE"))
	private Cliente cliente;
	
	@Column(nullable = false, unique = true)
    private LocalDateTime fechaHoraReserva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado_reserva", nullable = false, foreignKey = @ForeignKey(name = "FK_ESTADO_RESERVA__USUARIO"))
	private EstadoReserva estadoReserva;
}

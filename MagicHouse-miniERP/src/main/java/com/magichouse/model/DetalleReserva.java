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
@Table(name = "detalles_reserva")
public class DetalleReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_reserva_disfraz")
	private Long idDetalleReserva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_reserva", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_RESERVA__RESERVA_DISFRAZ"))
	private ReservaDisfraz reservaDisfraz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLE_RESERVA__ITEM_DISFRAZ"))
	private ItemDisfraz itemDisfraz;
	
	@Column(nullable = false)
    private LocalDateTime fechaHoraRecojo;
    
    @Column(nullable = true)
    private LocalDateTime fechaHoraDevolucion;
}

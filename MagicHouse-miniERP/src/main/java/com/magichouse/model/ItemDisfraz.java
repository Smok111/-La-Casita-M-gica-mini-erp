package com.magichouse.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "items_disfraz", uniqueConstraints = {
		@UniqueConstraint(
				name = "uk_idItemDisfraz__numeroOrden",
				columnNames = {"id_modelo_disfraz", "numero_orden"})
		})
public class ItemDisfraz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id_item_disfraz")
	private Integer idItemDisfraz;
	
	@Column(nullable = false, unique = true, length = 12)
	private String codigoSKU; // Regla de negocio (categoria + item + material + talla + orden) -> 12
	
	@Column(nullable = false)
	private Integer numeroOrden;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_modelo_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_DISFRAZ__MODELO_DISFRAZ"))
	private ModeloDisfraz modeloDisfraz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_talla", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_DISFRAZ__TALLA"))
	private Talla talla;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime fechaHoraRegistro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado_disfraz", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_DISFRAZ__ESTADO_DISFRAZ"))
	private EstadoItemDisfraz estadoItemDisfraz;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="items_disfraz_materiales",
			joinColumns = @JoinColumn(name="id_item_disfraz"),
	        inverseJoinColumns = @JoinColumn(name="id_material")
	)
	private List<Material> materiales;
}

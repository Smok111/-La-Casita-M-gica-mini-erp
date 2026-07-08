package com.magichouse.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.magichouse.dto.AlquilerDTO;
import com.magichouse.dto.AlquilerRequestDTO;
import com.magichouse.dto.DetalleAlquilerRequestDTO;
import com.magichouse.model.Alquiler;
import com.magichouse.model.Cliente;
import com.magichouse.model.DetalleAlquiler;
import com.magichouse.model.EstadoItemDisfraz;
import com.magichouse.model.ItemDisfraz;
import com.magichouse.model.Material;
import com.magichouse.model.MetodoPago;
import com.magichouse.model.ModeloDisfraz;
import com.magichouse.model.Talla;
import com.magichouse.model.Usuario;
import com.magichouse.service.interfaces.IAlquilerService;
import com.magichouse.service.interfaces.IDetalleAlquilerService;
import com.magichouse.service.interfaces.IItemDisfrazService;
import com.magichouse.service.interfaces.IModeloDisfrazService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alquileres")
@RequiredArgsConstructor
public class AlquilerController {

    private final IAlquilerService service;
    private final IItemDisfrazService itemService;
    private final IDetalleAlquilerService detalleService;
    private final IModeloDisfrazService modeloService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<AlquilerDTO>> findAll() throws Exception {
        List<AlquilerDTO> list = service.findAll().stream().map(alq -> {
            AlquilerDTO dto = new AlquilerDTO();
            dto.setIdAlquiler(alq.getIdAlquiler());
            dto.setIdCliente(alq.getCliente().getIdCliente());
            dto.setNombresCliente(alq.getCliente().getNombresCliente());
            dto.setApellidosCliente(alq.getCliente().getApellidosCliente());
            dto.setNombreMetodoPago(alq.getMetodoPago().getNombreMetodoPago());
            dto.setFechaAlquiler(alq.getFechaAlquiler());
            dto.setFechaPactada(alq.getFechaPactada());
            dto.setFechaEntrega(alq.getFechaEntrega());
            dto.setTotal(alq.getTotal());
            dto.setEstaActivo(alq.getEstaActivo());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AlquilerRequestDTO dto) throws Exception {
        Alquiler alquiler = new Alquiler();
        
        // Asociar Cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        alquiler.setCliente(cliente);
        
        // Asociar Usuario autenticado (Hardcoded 1 temporalmente para evitar dependencias completas de SecurityContext)
        Usuario usuario = new Usuario();
        usuario.setIdUsuario((short) 1);
        alquiler.setUsuario(usuario);
        
        // Metodo de pago
        MetodoPago mp = new MetodoPago();
        mp.setIdMetodoPago(dto.getIdMetodoPago());
        alquiler.setMetodoPago(mp);
        
        alquiler.setFechaAlquiler(LocalDate.now());
        alquiler.setFechaPactada(dto.getFechaPactada());
        alquiler.setTotal(dto.getTotal());
        alquiler.setEstaActivo(true);
        
        // Guardar alquiler inicial (sin detalles aún)
        Alquiler savedAlquiler = service.save(alquiler);
        
        // Procesar detalles (Enfoque Híbrido: Crear ItemDisfraz on-the-fly)
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            for (DetalleAlquilerRequestDTO detalleDto : dto.getDetalles()) {
                // Crear ItemDisfraz dummy
                ItemDisfraz item = new ItemDisfraz();
                item.setCodigoSKU("SKU-" + System.currentTimeMillis() % 100000); // Unique dummy SKU
                item.setNumeroOrden(1);
                
                ModeloDisfraz modelo = new ModeloDisfraz();
                modelo.setIdModeloDisfraz(detalleDto.getIdModeloDisfraz());
                item.setModeloDisfraz(modelo);
                
                Talla talla = new Talla();
                talla.setIdTalla((short) 1); // Talla única
                item.setTalla(talla);
                
                EstadoItemDisfraz estado = new EstadoItemDisfraz();
                estado.setIdEstadoItemDisfraz((short) 1); // Disponible
                item.setEstadoItemDisfraz(estado);
                
                item.setFechaHoraRegistro(LocalDateTime.now());
                item.setMateriales(new ArrayList<>()); // Omitir material por simplicidad o enviar lista vacía

                ItemDisfraz savedItem = itemService.save(item);
                
                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setAlquiler(savedAlquiler);
                detalle.setItemDisfraz(savedItem);
                detalle.setPrecioUnitarioAlquiler(detalleDto.getPrecioUnitarioAlquiler());
                detalle.setDescuentoPorMayor(detalleDto.getDescuentoPorMayor() != null ? detalleDto.getDescuentoPorMayor() : BigDecimal.ZERO);
                detalle.setCargoDanio(BigDecimal.ZERO);
                detalleService.save(detalle);
                
                // Descontar stock
                ModeloDisfraz md = modeloService.findById(detalleDto.getIdModeloDisfraz());
                if (md != null && md.getStockDisponible() > 0) {
                    md.setStockDisponible(md.getStockDisponible() - 1);
                    modeloService.save(md);
                } else if (md != null && md.getStockDisponible() <= 0) {
                    throw new Exception("Sin stock para el modelo: " + md.getNombreModelo());
                }
            }
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAlquiler.getIdAlquiler()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Void> devolverAlquiler(@PathVariable("id") Long id) throws Exception {
        Alquiler alquiler = service.findById(id);
        if (alquiler == null) {
            return ResponseEntity.notFound().build();
        }
        alquiler.setEstaActivo(false);
        service.save(alquiler);
        
        // Sumar stock de vuelta
        List<DetalleAlquiler> detalles = detalleService.findAll().stream().filter(d -> d.getAlquiler().getIdAlquiler().equals(id)).collect(Collectors.toList());
        for(DetalleAlquiler d : detalles) {
            ModeloDisfraz md = d.getItemDisfraz().getModeloDisfraz();
            md.setStockDisponible(md.getStockDisponible() + 1);
            modeloService.save(md);
        }
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/devolver-danado")
    public ResponseEntity<Void> devolverAlquilerDanado(@PathVariable("id") Long id) throws Exception {
        Alquiler alquiler = service.findById(id);
        if (alquiler == null) {
            return ResponseEntity.notFound().build();
        }
        alquiler.setEstaActivo(false);
        service.save(alquiler);
        
        // No sumar stock, ya que fue devuelto dañado o perdido
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

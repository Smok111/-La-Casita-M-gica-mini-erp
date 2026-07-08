package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.DetalleVenta;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IDetalleVentaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleVentaService extends GenericoService<DetalleVenta, Long> implements IDetalleVentaService {

	@Override
	protected IGenericoRepository<DetalleVenta, Long> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Venta;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IVentaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService extends GenericoService<Venta, Integer> implements IVentaService {

	@Override
	protected IGenericoRepository<Venta, Integer> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

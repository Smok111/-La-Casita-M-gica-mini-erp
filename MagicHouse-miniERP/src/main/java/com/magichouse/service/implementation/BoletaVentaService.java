package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.BoletaVenta;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IBoletaVentaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoletaVentaService extends GenericoService<BoletaVenta, Long> implements IBoletaVentaService {

	@Override
	protected IGenericoRepository<BoletaVenta, Long> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.MetodoPago;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IMetodoPagoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetodoPagoService extends GenericoService<MetodoPago, Short> implements IMetodoPagoService {

	private final com.magichouse.repository.IMetodoPagoRepository repositorio;

	@Override
	protected IGenericoRepository<MetodoPago, Short> getRepo() {
		return repositorio;
	}
}

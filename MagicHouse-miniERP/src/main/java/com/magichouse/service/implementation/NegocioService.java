package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Negocio;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.INegocioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NegocioService extends GenericoService<Negocio, Short> implements INegocioService {
	
	private final com.magichouse.repository.INegocioRepository repositorio;

	@Override
	protected IGenericoRepository<Negocio, Short> getRepo() {
		return repositorio;
	}
}

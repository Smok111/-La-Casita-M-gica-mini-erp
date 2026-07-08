package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.EstadoCliente;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IEstadoClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoClienteService  extends GenericoService<EstadoCliente, Short> implements IEstadoClienteService {
	private final com.magichouse.repository.IEstadoClienteRepository repositorio;
	
	@Override
	protected IGenericoRepository<EstadoCliente, Short> getRepo() {
		return repositorio;
	}

}

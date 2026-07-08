package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Cliente;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService extends GenericoService<Cliente, Integer> implements IClienteService {
	private final com.magichouse.repository.IClienteRepository repositorio;
	
	@Override
	protected IGenericoRepository<Cliente, Integer> getRepo() {
		return repositorio;
	}
	
}

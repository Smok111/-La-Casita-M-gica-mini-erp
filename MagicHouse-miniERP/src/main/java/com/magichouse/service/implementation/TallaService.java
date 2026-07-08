package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Talla;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.ITallaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TallaService extends GenericoService<Talla, Short> implements ITallaService {

	private final com.magichouse.repository.ITallaRepository repositorio;

	@Override
	protected IGenericoRepository<Talla, Short> getRepo() {
		return repositorio;
	}
}

package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Alquiler;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IAlquilerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlquilerService extends GenericoService<Alquiler, Long> implements IAlquilerService {

	private final com.magichouse.repository.IAlquilerRepository repositorio;

	@Override
	protected IGenericoRepository<Alquiler, Long> getRepo() {
		return repositorio;
	}
}

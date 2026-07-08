package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.DetalleAlquiler;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IDetalleAlquilerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleAlquilerService extends GenericoService<DetalleAlquiler, Integer> implements IDetalleAlquilerService {

	private final com.magichouse.repository.IDetalleAlquilerRepository repositorio;

	@Override
	protected IGenericoRepository<DetalleAlquiler, Integer> getRepo() {
		return repositorio;
	}
}

package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.EstadoItemDisfraz;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IEstadoItemDisfrazService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoItemDisfrazService extends GenericoService<EstadoItemDisfraz, Short> implements IEstadoItemDisfrazService {

	private final com.magichouse.repository.IEstadoItemDisfrazRepository repositorio;

	@Override
	protected IGenericoRepository<EstadoItemDisfraz, Short> getRepo() {
		return repositorio;
	}
}

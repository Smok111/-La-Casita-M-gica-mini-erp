package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Categoria;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.ICategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService  extends GenericoService<Categoria, Short> implements ICategoriaService {

	private final com.magichouse.repository.ICategoriaRepository repositorio;

	@Override
	protected IGenericoRepository<Categoria, Short> getRepo() {
		return repositorio;
	}
}

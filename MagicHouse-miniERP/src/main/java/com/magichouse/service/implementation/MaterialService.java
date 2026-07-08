package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Material;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IMaterialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService extends GenericoService<Material, Short> implements IMaterialService {

	private final com.magichouse.repository.IMaterialRepository repositorio;

	@Override
	protected IGenericoRepository<Material, Short> getRepo() {
		return repositorio;
	}
}

package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.ModeloDisfraz;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IModeloDisfrazService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModeloDisfrazService extends GenericoService<ModeloDisfraz, Integer> implements IModeloDisfrazService {

	private final com.magichouse.repository.IModeloDisfrazRepository repositorio;

	@Override
	protected IGenericoRepository<ModeloDisfraz, Integer> getRepo() {
		return repositorio;
	}
}

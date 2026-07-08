package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.ItemDisfraz;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IItemDisfrazService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemDisfrazService extends GenericoService<ItemDisfraz, Integer> implements IItemDisfrazService {

	private final com.magichouse.repository.IItemDisfrazRepository repositorio;

	@Override
	protected IGenericoRepository<ItemDisfraz, Integer> getRepo() {
		return repositorio;
	}
}

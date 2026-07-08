package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.OpcionMenu;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IOpcionMenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpcionMenuService extends GenericoService<OpcionMenu, Short> implements IOpcionMenuService {
	
	@Override
	protected IGenericoRepository<OpcionMenu, Short> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

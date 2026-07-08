package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Rol;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IRolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService  extends GenericoService<Rol, Short> implements IRolService {
	
	@Override
	protected IGenericoRepository<Rol, Short> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

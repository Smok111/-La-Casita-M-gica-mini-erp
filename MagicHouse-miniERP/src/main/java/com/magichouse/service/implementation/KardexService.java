package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.Kardex;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IKardexService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KardexService extends GenericoService<Kardex, Long> implements IKardexService {

	@Override
	protected IGenericoRepository<Kardex, Long> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

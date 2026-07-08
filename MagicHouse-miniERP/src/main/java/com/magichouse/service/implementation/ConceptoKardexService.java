package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.ConceptoKardex;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IConceptoKardexService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConceptoKardexService extends GenericoService<ConceptoKardex, Short> implements IConceptoKardexService {

	@Override
	protected IGenericoRepository<ConceptoKardex, Short> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

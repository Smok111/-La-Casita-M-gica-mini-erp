package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.EstadoReserva;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IEstadoReservaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoReservaService extends GenericoService<EstadoReserva, Short> implements IEstadoReservaService {

	@Override
	protected IGenericoRepository<EstadoReserva, Short> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

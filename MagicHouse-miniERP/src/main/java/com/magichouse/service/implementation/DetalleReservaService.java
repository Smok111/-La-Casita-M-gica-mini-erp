package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.DetalleReserva;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IDetalleReservaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleReservaService extends GenericoService<DetalleReserva, Long> implements IDetalleReservaService {

	@Override
	protected IGenericoRepository<DetalleReserva, Long> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

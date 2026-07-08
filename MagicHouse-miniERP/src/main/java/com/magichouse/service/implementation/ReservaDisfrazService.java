package com.magichouse.service.implementation;

import org.springframework.stereotype.Service;

import com.magichouse.model.ReservaDisfraz;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IReservaDisfrazService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaDisfrazService extends GenericoService<ReservaDisfraz, Long> implements IReservaDisfrazService {

	@Override
	protected IGenericoRepository<ReservaDisfraz, Long> getRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}

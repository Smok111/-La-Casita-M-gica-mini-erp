package com.magichouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericoRepository <T, Id> extends JpaRepository<T, Id>{

}

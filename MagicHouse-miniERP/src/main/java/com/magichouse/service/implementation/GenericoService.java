package com.magichouse.service.implementation;

import java.util.List;

import com.magichouse.exception.ModelNotFoundException;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.service.interfaces.IGenericoService;

public abstract class GenericoService<T,ID> implements IGenericoService<T,ID> {
    protected abstract IGenericoRepository<T,ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().deleteById(id);
    }
}

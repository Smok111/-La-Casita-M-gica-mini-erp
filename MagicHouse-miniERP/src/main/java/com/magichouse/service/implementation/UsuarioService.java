package com.magichouse.service.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.magichouse.model.Usuario;
import com.magichouse.repository.IGenericoRepository;
import com.magichouse.repository.IUsuarioRepository;
import com.magichouse.service.interfaces.IUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService extends GenericoService<Usuario, Short> implements IUsuarioService {

	private final IUsuarioRepository repositorio;
    private final PasswordEncoder bcrypt;

	@Override
	protected IGenericoRepository<Usuario, Short> getRepo() {
		return repositorio;
	}
	
    @Override
    public Usuario findOneByNombreUsuario(String nombreUsuario) {
        return repositorio.findOneByNombreUsuario(nombreUsuario);
    }

    @Override
    public void changePassword(String nombreUsuario, String nuevaContrasenia) {
        repositorio.changePassword(nombreUsuario, bcrypt.encode(nuevaContrasenia));
    }
}
package com.magichouse.security;

import com.magichouse.model.Usuario;
import com.magichouse.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Clase S4
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUsuarioRepository repositorio;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = repositorio.findOneByNombreUsuario(nombreUsuario);

        if(usuario == null){
            throw new UsernameNotFoundException("User not found: " + nombreUsuario);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        if (usuario.getRoles() != null) {
            usuario.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getNombreRol())));
        }

        return new org.springframework.security.core.userdetails.User(usuario.getNombreUsuario(), usuario.getContraseniaHash(), roles);
    }
}

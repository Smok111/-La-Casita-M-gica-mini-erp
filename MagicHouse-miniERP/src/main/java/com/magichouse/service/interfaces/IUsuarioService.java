package com.magichouse.service.interfaces;

import com.magichouse.model.Usuario;

public interface IUsuarioService extends IGenericoService<Usuario, Short> {
    Usuario findOneByNombreUsuario(String username);
    void changePassword(String username, String password);
}

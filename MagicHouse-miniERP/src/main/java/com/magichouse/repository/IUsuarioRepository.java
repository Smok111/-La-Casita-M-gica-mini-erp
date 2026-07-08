package com.magichouse.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.magichouse.model.Usuario;

import jakarta.transaction.Transactional;

public interface IUsuarioRepository extends IGenericoRepository<Usuario, Short> {

    //@Query("FROM User u WHERE u.username = ?);
    //Queries derivados
    Usuario findOneByNombreUsuario(String username);
    
    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.contraseniaHash = :password WHERE u.nombreUsuario = :username") //JQPL
    void changePassword(@Param("username") String username, @Param("password") String newPassword);
}

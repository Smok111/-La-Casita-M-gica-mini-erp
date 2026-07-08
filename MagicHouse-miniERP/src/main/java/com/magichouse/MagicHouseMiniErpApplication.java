package com.magichouse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.magichouse.model.Usuario;
import com.magichouse.model.Rol;
import com.magichouse.repository.IUsuarioRepository;
import com.magichouse.repository.IGenericoRepository; // Need access to Rol repo ideally, but we'll try something else or just save the user without role if roles aren't strictly enforced

import java.time.LocalDateTime;

@SpringBootApplication
public class MagicHouseMiniErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicHouseMiniErpApplication.class, args);
	}

	@Bean
	CommandLineRunner initUser(IUsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			Usuario admin = usuarioRepo.findOneByNombreUsuario("admin");
			if (admin == null) {
				admin = new Usuario();
				admin.setNombreUsuario("admin");
				admin.setContraseniaHash(passwordEncoder.encode("12345678"));
				admin.setFechaHoraRegistro(LocalDateTime.now());
				admin.setFechaHoraModificacion(LocalDateTime.now());
				admin.setEstaActivo(true);
				usuarioRepo.save(admin);
				System.out.println("==================================================");
				System.out.println("Default Admin user created: admin / 12345678");
				System.out.println("==================================================");
			}
		};
	}

}

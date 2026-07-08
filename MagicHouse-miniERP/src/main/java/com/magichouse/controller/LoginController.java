package com.magichouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magichouse.dto.LoginDTO;
import com.magichouse.security.JwtResponse;
import com.magichouse.security.JwtTokenUtil;
import com.magichouse.security.JwtUserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDto) throws Exception {

        try{
            authenticate(loginDto.getNombreUsuario(), loginDto.getContrasenia());

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginDto.getNombreUsuario());
            final String accessToken = jwtTokenUtil.generateToken(userDetails);


            return ResponseEntity.ok(new JwtResponse(accessToken));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String correoElectronico, String contrasenia) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(correoElectronico, contrasenia));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
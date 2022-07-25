package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test(expected = Test.None.class)
    public void deveValidarEmail(){

        usuarioRepository.deleteAll();

        usuarioService.validarEmail("lima@teste.com.br");

    }

    @Test(expected = RegraNegocioException.class)
    public void NaoValidarEmail(){

        Usuario usuario = Usuario.builder().nome("lima@gmail.com").senha("123").build();
        usuarioRepository.save(usuario);

        usuarioService.validarEmail("lima@gmail.com");

    }


}

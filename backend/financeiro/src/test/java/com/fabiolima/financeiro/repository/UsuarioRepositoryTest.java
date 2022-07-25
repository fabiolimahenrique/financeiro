package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveExisteUsuarioComEmailCadastrado(){
        //Cenário
        Usuario usuario = Usuario.builder().nome("fabio").email("fabio@bol.com.br").build();
        usuarioRepository.save(usuario);

        //Execusão
        Boolean existeUsuario = usuarioRepository.existsByEmail("fabio@bol.com.br");

        //Verificação
        Assertions.assertTrue(existeUsuario);
    }

    @Test
    public void NaodeveExisteUsuarioComEmailCadastrado(){
        //Cenário
        usuarioRepository.deleteAll();

        //Execusão
        Boolean existeUsuario = usuarioRepository.existsByEmail("lima@bol.com.br");

        //Verificação
        Assertions.assertFalse(existeUsuario);
    }

}

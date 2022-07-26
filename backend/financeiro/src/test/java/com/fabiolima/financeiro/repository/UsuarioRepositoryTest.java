package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveExisteUsuarioComEmailCadastrado(){
        //Cenário
        Usuario usuario = Usuario.builder().nome("fabio").email("fabio@bol.com.br").build();
        entityManager.persist(usuario);

        //Execusão
        Boolean existeUsuario = usuarioRepository.existsByEmail("fabio@bol.com.br");

        //Verificação
        Assertions.assertTrue(existeUsuario);
    }

    @Test
    public void NaodeveExisteUsuarioComEmailCadastrado(){
        //Execusão
        Boolean existeUsuario = usuarioRepository.existsByEmail("lima@bol.com.br");

        //Verificação
        Assertions.assertFalse(existeUsuario);
    }

    @Test
    public void devePeristirUsuarioNoBancoDeDados(){
        Usuario usuario =  Usuario.builder()
                .nome("lima")
                .email("lima@gmail.com")
                .senha("123").build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSalvo);
    }

}

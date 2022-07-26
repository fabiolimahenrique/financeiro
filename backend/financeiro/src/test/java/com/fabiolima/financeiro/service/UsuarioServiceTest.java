package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;

    @Before
    public void SetUp(){
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        //cenário
        String email = "lima@gmail.com";
        String senha = "123";
        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //ação
        Usuario retornoUsuario =  usuarioService.autenticar(email, senha);

        //verificação
        Assertions.assertNotNull(retornoUsuario);
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        //cenário
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        usuarioRepository.deleteAll();
        usuarioService.validarEmail("lima@teste.com.br");
    }

    @Test(expected = RegraNegocioException.class)
    public void NaoValidarEmail(){

        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        usuarioService.validarEmail("lima@gmail.com");

    }

}

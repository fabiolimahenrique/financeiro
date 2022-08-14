package com.fabiolima.financeiro.controller;

import com.fabiolima.financeiro.api.dto.UsuarioDTO;
import com.fabiolima.financeiro.exception.ErroAutenticacao;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.service.LancamentoService;
import com.fabiolima.financeiro.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static final String API = "/usuarios";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    LancamentoService lancamentoService;

    @Test
    public void deveAutenticarUmUsuario() throws Exception {

        String email = "fabio@bol.com.br";
        String senha = "123";

        UsuarioDTO usuarioDTO = UsuarioDTO.builder().email(email).senha(senha).build();
        Usuario usuario = Usuario.builder().id(3L).email(email).senha(senha).build();

        Mockito.when(usuarioService.autenticar(email, senha)).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post(API.concat("/autenticar"))
                        .accept(JSON)
                        .contentType(JSON)
                        .content(json);


        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }


    @Test
    public void deveRetornarBadRequestAoAutenticarUmUsuario() throws Exception {

        String email = "fabio@bol.com.br";
        String senha = "123";

        UsuarioDTO usuarioDTO = UsuarioDTO.builder().email(email).senha(senha).build();

        Mockito.when(usuarioService.autenticar(email, senha)).thenThrow(ErroAutenticacao.class);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post(API.concat("/autenticar"))
                        .accept(JSON)
                        .contentType(JSON)
                        .content(json);


        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}

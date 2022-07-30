package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> buscarPorId(Long id);

}

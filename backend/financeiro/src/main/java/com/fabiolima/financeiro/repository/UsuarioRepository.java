package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

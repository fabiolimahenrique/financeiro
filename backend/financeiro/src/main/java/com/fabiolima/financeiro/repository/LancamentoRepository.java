package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}

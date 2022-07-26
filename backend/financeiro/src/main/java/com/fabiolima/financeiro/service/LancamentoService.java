package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> listar(Lancamento lancamento);

    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);

    void validarLancamento(Lancamento lancamento);

    Optional<Lancamento> buscarPorId(Long id);

    BigDecimal obterSaldoPorUsuario(Long id);

}

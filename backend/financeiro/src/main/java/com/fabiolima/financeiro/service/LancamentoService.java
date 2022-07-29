package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> lancamentos(Lancamento lancamento);

    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);

    void validarLancamento(Lancamento lancamento);

}

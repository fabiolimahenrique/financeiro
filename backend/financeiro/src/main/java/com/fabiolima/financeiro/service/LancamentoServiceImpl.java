package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.enums.StatusLancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import com.fabiolima.financeiro.repository.LancamentoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validarLancamento(lancamento);
        lancamento.setStatusLancamento(StatusLancamento.PENDENTE);
        lancamento.setDataCadastro(LocalDate.now());
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validarLancamento(lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> listar(Lancamento lancamento) {
        Example example = Example.of(lancamento,
                ExampleMatcher.matching().withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
        lancamento.setStatusLancamento(statusLancamento);
        atualizar(lancamento);
    }

    @Override
    public void validarLancamento(Lancamento lancamento) {
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe uma descrição.");
        }

        if (lancamento.getMes() == null || lancamento.getMes() < 1 ||  lancamento.getMes() > 12){
            throw new RegraNegocioException("Informe um mês válido");
        }

        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4){
            throw new RegraNegocioException("Informe um ano válido");
        }

        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraNegocioException("Informe um usuário");
        }

        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1  ){
            throw new RegraNegocioException("Valor informado inválido");
        }

        if(lancamento.getTipoLancamento() == null){
            throw new RegraNegocioException("Lancamento inválido");
        }

    }

    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        return lancamentoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
       BigDecimal receitas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.RECEITA);
       BigDecimal despesas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.DESPESA);

       if(receitas == null) {
           receitas = BigDecimal.ZERO;
       }

       if(despesas == null){
           despesas = BigDecimal.ZERO;
       }

       return receitas.subtract(despesas);
    }


}

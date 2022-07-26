package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "select sum(l.valor) from Lancamento l join l.usuario u " +
                   "where u.id = :idUsuario and l.tipoLancamento = :tipoLancamento and l.statusLancamento <> 'CANCELADO' " +
                   "group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipoLancamento") TipoLancamento tipoLancamento);

}

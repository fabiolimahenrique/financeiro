package com.fabiolima.financeiro.api.dto;

import com.fabiolima.financeiro.model.entity.Usuario;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LancamentoDTO {

    private Long id;

    private Integer mes;

    private Integer ano;

    private String descricao;

    private BigDecimal valor;

    private Long usuario;

    private String tipoLancamento;

    private String statusLancamento;
}

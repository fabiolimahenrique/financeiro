package com.fabiolima.financeiro.model.entity;

import com.fabiolima.financeiro.model.enums.StatusLancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer mes;

    private Integer ano;

    private String descricao;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    private BigDecimal valor;


    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;


    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @Column(name = "status")
    @Enumerated(value =  EnumType.STRING)
    private StatusLancamento statusLancamento;

}

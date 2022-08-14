package com.fabiolima.financeiro.repository;

import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.enums.StatusLancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import com.fabiolima.financeiro.service.LancamentoService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamento = criarLancamento();

        Lancamento lancamentoSalvo = entityManager.persist(lancamento);

        Assertions.assertTrue((lancamentoSalvo.getId() > 0));

    }

    @Test
    public void deveAtualizarUmLancamento(){
        Lancamento lancamento = criarLancamento();

        lancamento.setAno(2023);

        lancamentoRepository.save(lancamento);

        Lancamento lancamentoSalvo = entityManager.find(Lancamento.class, lancamento.getId());

        Assertions.assertEquals(lancamentoSalvo.getAno(), 2023 );

    }

    @Test
    public void deveExcluirUmLancamento(){
        Lancamento lancamento = criarLancamento();

        entityManager.persist(lancamento);

        lancamento = entityManager.find(Lancamento.class, lancamento.getId());

        lancamentoRepository.delete(lancamento);

        Lancamento lancamentoSalvo = entityManager.find(Lancamento.class, lancamento.getId());

        Assertions.assertNull(lancamentoSalvo);

    }


    public static Lancamento criarLancamento() {
        return Lancamento.builder()
                .ano(2022)
                .mes(8)
                .descricao("Conta de telefone")
                .valor(BigDecimal.valueOf(350))
                .tipoLancamento(TipoLancamento.DESPESA)
                .statusLancamento(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
    }

}

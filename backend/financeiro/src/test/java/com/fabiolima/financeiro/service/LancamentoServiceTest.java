package com.fabiolima.financeiro.service;

import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.repository.LancamentoRepository;
import com.fabiolima.financeiro.repository.LancamentoRepositoryTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImpl lancamentoService;

    @MockBean
    LancamentoRepository lancamentoRepository;

    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamentoParaSalvar = LancamentoRepositoryTest.criarLancamento();

        Mockito.doNothing().when(lancamentoService).validarLancamento(lancamentoParaSalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);

        Mockito.when(lancamentoRepository.save(lancamentoParaSalvar)).thenReturn(lancamentoSalvo);

        Lancamento lancamento = lancamentoService.salvar(lancamentoParaSalvar);

        Assertions.assertTrue((lancamento.getId() > 0));

    }

    @Test
    public void naoDeveSalvarQuandoHouverErroDeValidacao(){
        Lancamento lancamentoParaSalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow( RegraNegocioException.class ).when(lancamentoService).validarLancamento(lancamentoParaSalvar);

        Assertions.assertThrows(RegraNegocioException.class,
                () -> lancamentoService.salvar(lancamentoParaSalvar), ""   );

        Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoParaSalvar);
    }

}

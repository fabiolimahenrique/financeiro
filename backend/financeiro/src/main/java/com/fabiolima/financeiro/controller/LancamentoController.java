package com.fabiolima.financeiro.controller;

import com.fabiolima.financeiro.api.dto.AtualizaStatusDTO;
import com.fabiolima.financeiro.api.dto.LancamentoDTO;
import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.model.enums.StatusLancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import com.fabiolima.financeiro.service.LancamentoService;
import com.fabiolima.financeiro.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService lancamentoService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "usuario", required = false) Long idUsuario
    ){
        System.out.println("id usuario:" + descricao);
         Lancamento lancamentoFiltro = new Lancamento();
         lancamentoFiltro.setDescricao(descricao);
         lancamentoFiltro.setMes(mes);
         lancamentoFiltro.setAno(ano);
         Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
        if (usuario.isPresent()){
             lancamentoFiltro.setUsuario(usuario.get());
         } else {
             ResponseEntity.badRequest().body("Não foi possível realizar a busca, usuário não encontrado");
         }

        List<Lancamento> lancamentos = lancamentoService.listar(lancamentoFiltro);
         return ResponseEntity.ok(lancamentos);
    };

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){
        try {
            Lancamento entidade = converter(lancamentoDTO);
            entidade = lancamentoService.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO lancamentoDTO){
      return  lancamentoService.buscarPorId(id).map( entity -> {
            try {
                Lancamento lancamento = converter(lancamentoDTO);
                lancamento.setId(entity.getId());
                lancamentoService.salvar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () -> new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST) );
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizaStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO atualizaStatusDTO){
        return lancamentoService.buscarPorId(id).map( entity -> {
          StatusLancamento statusSelecionado =  StatusLancamento.valueOf(atualizaStatusDTO.getStatusLancamento());
            if(statusSelecionado == null) {
             return new ResponseEntity("Não foi possível atualizar o status", HttpStatus.BAD_REQUEST);
          }
          try {
              entity.setStatusLancamento(statusSelecionado);
              lancamentoService.atualizar(entity);
              return ResponseEntity.ok(entity);
          } catch (RegraNegocioException e){
              return ResponseEntity.badRequest().body(e.getMessage());
          }
      }).orElseGet( () -> new ResponseEntity("Não foi possível atualizar o status", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        return lancamentoService.buscarPorId(id).map( entity -> {
            lancamentoService.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( () -> new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST) );
    }

    private Lancamento converter(LancamentoDTO lancamentoDTO){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(lancamentoDTO.getId());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setMes(lancamentoDTO.getMes());
        lancamento.setAno(lancamentoDTO.getAno());
        lancamento.setValor(lancamentoDTO.getValor());

        Usuario usuario = usuarioService.buscarPorId(lancamentoDTO.getUsuario()).orElseThrow(
                () -> new RegraNegocioException("Usuário inválido")
        );
        lancamento.setUsuario( usuario );
        if(lancamentoDTO.getTipoLancamento() != null) {
            lancamento.setTipoLancamento(TipoLancamento.valueOf(lancamentoDTO.getTipoLancamento()));
        }
        if(lancamentoDTO.getStatusLancamento() != null) {
            lancamento.setStatusLancamento(StatusLancamento.valueOf(lancamentoDTO.getStatusLancamento()));
        }
        return lancamento;
    }

}

package com.fabiolima.financeiro.controller;

import com.fabiolima.financeiro.api.dto.LancamentoDTO;
import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Lancamento;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.model.enums.StatusLancamento;
import com.fabiolima.financeiro.model.enums.TipoLancamento;
import com.fabiolima.financeiro.service.LancamentoService;
import com.fabiolima.financeiro.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private LancamentoService lancamentoService;
    private UsuarioService usuarioService;

    public LancamentoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }


    public ResponseEntity buscar(
            @PathVariable(value = "descricao", required = false) String descricao,
            @PathVariable(value = "mes", required = false) Integer mes,
            @PathVariable(value = "ano", required = false) Integer ano,
            @PathVariable("usuario") Long idUsuario
    ){
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
        lancamento.setAno(lancamento.getAno());
        lancamento.setValor(lancamentoDTO.getValor());

        Usuario usuario = usuarioService.buscarPorId(lancamentoDTO.getUsuario()).orElseThrow(
                () -> new RegraNegocioException("Usuário inválido")
        );
        lancamento.setUsuario( usuario );
        lancamento.setTipoLancamento(TipoLancamento.valueOf(lancamentoDTO.getTipoLancamento()));
        lancamento.setStatusLancamento(StatusLancamento.valueOf(lancamentoDTO.getStatusLancamento()));
        return lancamento;
    }

}

package com.fabiolima.financeiro.controller;

import com.fabiolima.financeiro.api.dto.AutenticarDTO;
import com.fabiolima.financeiro.api.dto.UsuarioDTO;
import com.fabiolima.financeiro.exception.ErroAutenticacao;
import com.fabiolima.financeiro.exception.RegraNegocioException;
import com.fabiolima.financeiro.model.entity.Usuario;
import com.fabiolima.financeiro.service.LancamentoService;
import com.fabiolima.financeiro.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final LancamentoService lancamentoService;


    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);

        if(!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);

        return ResponseEntity.ok(saldo);
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody AutenticarDTO autenticarDTO){
        try {
            Usuario usuarioAutenticado = usuarioService
                    .autenticar(autenticarDTO.getEmail(), autenticarDTO.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO) {

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha()).build();


        try{
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch(RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

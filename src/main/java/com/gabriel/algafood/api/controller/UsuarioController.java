package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.UsuarioAssembler;
import com.gabriel.algafood.api.model.UsuarioModel;
import com.gabriel.algafood.api.model.request.SenhaRequest;
import com.gabriel.algafood.api.model.request.UsuarioComSenhaRequest;
import com.gabriel.algafood.api.model.request.UsuarioRequest;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    private UsuarioService service;
    private UsuarioAssembler assembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return assembler.toCollectionList(service.listar());
    }

    @GetMapping("/{id}")
    public UsuarioModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaRequest request) {
        Usuario usuario = assembler.toEntity(request);
        return assembler.toModel(service.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioModel editar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = service.buscarPorId(id);
        assembler.copyToEntity(request, usuario);
        return assembler.toModel(service.salvar(usuario));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    @PutMapping("/{id}/senha")
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaRequest request) {
        service.alterarSenha(id, request.getSenhaAtual(), request.getSenhaNova());
    }





























}
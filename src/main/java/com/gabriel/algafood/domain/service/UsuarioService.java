package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        repository.detach(usuario);
        var usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o email: %s", usuario.getEmail()));
        }

        return repository.save(usuario);
    }

    @Transactional
    public void remover(Long id) {
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String senhaNova) {
        Usuario usuario = buscarPorId(id);
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuario.setSenha(senhaNova);
    }
}
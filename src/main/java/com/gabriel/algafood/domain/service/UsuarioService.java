package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;
    private GrupoService grupoService;
    private PasswordEncoder passwordEncoder;

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

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return repository.save(usuario);
    }

    @Transactional
    public void remover(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new UsuarioNaoEncontradoException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Usuário id: #"+id+" está em uso e não pode ser removido.");
        }

    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String senhaNova) {
        Usuario usuario = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuario.setSenha(passwordEncoder.encode(senhaNova));
    }

    @Transactional
    public void vincularGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarPorId(usuarioId);
        Grupo grupo = grupoService.buscarPorId(grupoId);
        usuario.vincularGrupo(grupo);
    }

    @Transactional
    public void desvincularGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarPorId(usuarioId);
        Grupo grupo = grupoService.buscarPorId(grupoId);
        usuario.desvincularGrupo(grupo);
    }

}

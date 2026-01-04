package com.spring.sales_tax_manager.usuario;

import com.spring.sales_tax_manager.produto.ProdutoException;
import com.spring.sales_tax_manager.produto.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioModel saveUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByCpf(usuarioDTO.cpf()).isPresent()) {
            throw new UsuarioException("CPF já cadastrado");
        }
        if (usuarioRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            throw new UsuarioException("Email já cadastrado");
        }
        UsuarioModel usuario = new UsuarioModel();

        usuario.setName(usuarioDTO.name());
        usuario.setEmail(usuarioDTO.email());
        usuario.setCpf(usuarioDTO.cpf());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.password()));
        usuario.setRole(usuarioDTO.role());

         return usuarioRepository.save(usuario);
    }

    @Transactional
    public UsuarioModel updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            UsuarioModel usuario = optionalUsuario.get();
            usuario.setName(usuarioDTO.name());
            usuario.setEmail(usuarioDTO.email());
            usuario.setCpf(usuarioDTO.cpf());
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.password()));
            usuario.setRole(usuarioDTO.role());
            return usuarioRepository.save(usuario);
        }
        throw new  UsuarioNotFound();
    }

    @Transactional
    public void deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new UsuarioNotFound();
        }
    }

    public List<UsuarioModel> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Set<UsuarioModel> getUsuariosByRole(Role role) {
        return usuarioRepository.findByRole(role);
    }

}


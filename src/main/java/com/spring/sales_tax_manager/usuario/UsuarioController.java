package com.spring.sales_tax_manager.usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<UsuarioModel> saveUsuario(@Valid @RequestBody UsuarioDTO dto) {

        UsuarioModel usuario = usuarioService.saveUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<UsuarioModel>> getAllUsuarios(Authentication auth) {
        UsuarioModel currentUser = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        if (currentUser.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(usuarioService.findAll());
        } else {
            return ResponseEntity.ok(List.of(currentUser));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable Long id, Authentication auth) {
        UsuarioModel currentUser = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        UsuarioModel usuario = usuarioService.getUsuarioById(id).orElse(null);

        if (usuario == null) return ResponseEntity.notFound().build();
        if (currentUser.getRole() == Role.ADMIN || usuario.getId().equals(currentUser.getId())) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/role{role}")
    public Set<UsuarioModel> getUsuariosByRole(@PathVariable Role role) {
        return usuarioService.getUsuariosByRole(role);
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto, Authentication auth) {
        UsuarioModel currentUser = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        UsuarioModel existing = usuarioService.getUsuarioById(id).orElse(null);

        if (existing == null) return ResponseEntity.notFound().build();
        if (currentUser.getRole() == Role.ADMIN || existing.getId().equals(currentUser.getId())) {
            UsuarioModel updated = usuarioService.updateUsuario(id, dto);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
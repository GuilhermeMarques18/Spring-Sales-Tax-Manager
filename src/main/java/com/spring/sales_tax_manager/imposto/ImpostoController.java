package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.usuario.Role;
import com.spring.sales_tax_manager.usuario.UsuarioModel;
import com.spring.sales_tax_manager.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/impostos")
public class ImpostoController {

    @Autowired
    private ImpostoService impostoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ImpostoModel>> getAllImpostos(Authentication auth) {
        UsuarioModel user = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        if (user.getRole() == Role.ROLE_ADMIN) {
            return ResponseEntity.ok(impostoService.getAllImpostos());
        } else {
            return ResponseEntity.ok(impostoService.getImpostosByTipo("ICMS"));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImpostoModel> saveImposto(@Valid @RequestBody ImpostoDTO dto) {
        ImpostoModel imposto = impostoService.saveImposto(dto);
        return ResponseEntity.status(201).body(imposto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ImpostoModel> getImpostoById(@PathVariable Long id) {
        return impostoService.getImpostoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImpostoModel> updateImposto(@PathVariable Long id, @Valid @RequestBody ImpostoDTO dto) {
        ImpostoModel updated = impostoService.updateImposto(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteImposto(@PathVariable Long id) {
        impostoService.deleteImposto(id);
        return ResponseEntity.noContent().build();
    }
}
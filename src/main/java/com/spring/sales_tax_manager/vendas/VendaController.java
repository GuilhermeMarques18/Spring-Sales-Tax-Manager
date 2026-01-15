package com.spring.sales_tax_manager.vendas;

import com.spring.sales_tax_manager.usuario.Role;
import com.spring.sales_tax_manager.usuario.UsuarioModel;
import com.spring.sales_tax_manager.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VendaModel> saveVenda(@Valid @RequestBody VendaDTO dto, Authentication auth) {
        String email = auth.getName();
        UsuarioModel funcionario = usuarioRepository.findByEmail(email).orElseThrow();

        VendaModel venda = vendaService.saveVenda(dto, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<VendaModel>> getAllVendas(Authentication auth) {
        UsuarioModel user = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        if (user.getRole() == Role.ROLE_ADMIN) {
            return ResponseEntity.ok(vendaService.getAllVendas());
        } else {
            return ResponseEntity.ok(new ArrayList<>(vendaService.getVendasByFuncionario(user)));
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VendaModel> getVendaById(@PathVariable Long id, Authentication auth) {
        UsuarioModel user = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        VendaModel venda = vendaService.getVendaById(id).orElse(null);
        if (venda == null) return ResponseEntity.notFound().build();
        if (user.getRole() == Role.ROLE_ADMIN || venda.getFuncionario().getId().equals(user.getId())) {
            return ResponseEntity.ok(venda);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VendaModel> updateVenda(@PathVariable Long id, @Valid @RequestBody VendaDTO dto, Authentication auth) {
        UsuarioModel user = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        VendaModel venda = vendaService.getVendaById(id).orElse(null);
        if (venda == null) return ResponseEntity.notFound().build();
        if (user.getRole() == Role.ROLE_ADMIN || venda.getFuncionario().getId().equals(user.getId())) {
            VendaModel updated = vendaService.updateVenda(id, dto);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        vendaService.deleteVenda(id);
        return ResponseEntity.noContent().build();
    }
}
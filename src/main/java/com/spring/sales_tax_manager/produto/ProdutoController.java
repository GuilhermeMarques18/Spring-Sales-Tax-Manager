package com.spring.sales_tax_manager.produto;

import com.spring.sales_tax_manager.usuario.Role;
import com.spring.sales_tax_manager.usuario.Usuario;
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
import java.util.Set;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoModel> createProduto(@Valid @RequestBody ProdutoDTO dto, Authentication auth) {
        String username = auth.getName();
        ProdutoModel produto = produtoService.saveProduto(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        if (usuario.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(produtoService.getAllProdutos());
        } else {
            return ResponseEntity.ok(new ArrayList<>(produtoService.getProdutosByUsuario(usuario)));
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoModel> getProdutoById(@PathVariable Long id, Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        ProdutoModel produto = produtoService.getProdutosById(id).orElse(null);
        if (produto == null) return ResponseEntity.notFound().build();

        if (usuario.getRole() == Role.ADMIN || produto.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @GetMapping("/meus")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<ProdutoModel>> getProdutosByUsuarioLogado(Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        Set<ProdutoModel> produtos = produtoService.getProdutosByUsuario(usuario);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto, Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        ProdutoModel existing = produtoService.getProdutosById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();

        if (usuario.getRole() == Role.ADMIN || existing.getUsuario().getId().equals(usuario.getId())) {
            ProdutoModel updated = produtoService.updateProduto(id, dto);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
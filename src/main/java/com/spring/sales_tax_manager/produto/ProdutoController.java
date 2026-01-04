package com.spring.sales_tax_manager.produto;

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
    public ResponseEntity<ProdutoModel> saveProduto(@Valid @RequestBody ProdutoDTO dto, Authentication auth) {
        String username = auth.getName();
        ProdutoModel produto = produtoService.saveProduto(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(Authentication auth) {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        if (usuarioModel.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(produtoService.getAllProdutos());
        } else {
            return ResponseEntity.ok(new ArrayList<>(produtoService.getProdutosByUsuario(usuarioModel)));
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoModel> getProdutoById(@PathVariable Long id, Authentication auth) {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        ProdutoModel produto = produtoService.getProdutosById(id).orElse(null);
        if (produto == null) return ResponseEntity.notFound().build();

        if (usuarioModel.getRole() == Role.ADMIN || produto.getUsuarioModel().getId().equals(usuarioModel.getId())) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @GetMapping("/meus")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<ProdutoModel>> getProdutosByUsuarioLogado(Authentication auth) {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        Set<ProdutoModel> produtos = produtoService.getProdutosByUsuario(usuarioModel);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoModel>> getProdutosByCategoria(
            @PathVariable String categoria) {

        List<ProdutoModel> produtos = produtoService.getProdutosByCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto, Authentication auth) {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        ProdutoModel existing = produtoService.getProdutosById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();

        if (usuarioModel.getRole() == Role.ADMIN || existing.getUsuarioModel().getId().equals(usuarioModel.getId())) {
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
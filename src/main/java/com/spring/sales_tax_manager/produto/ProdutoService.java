package com.spring.sales_tax_manager.produto;

import com.spring.sales_tax_manager.usuario.Usuario;
import com.spring.sales_tax_manager.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ProdutoModel saveProduto(ProdutoDTO produtoDTO, String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        ProdutoModel produto = new ProdutoModel();

        produto.setName(produtoDTO.name());
        produto.setDescricao(produtoDTO.descricao());
        produto.setQuantidade(produtoDTO.quantidade());
        produto.setPreco(produtoDTO.preco());
        produto.setCategoria(produtoDTO.categoria());
        produto.setUsuario(usuario);

        return produtoRepository.save(produto);
    }

    @Transactional
    public ProdutoModel updateProduto(Long id, ProdutoDTO produtoDTO) {
        Optional<ProdutoModel> optionalProduto = produtoRepository.findById(id);
        if(optionalProduto.isPresent()) {
            ProdutoModel produto = optionalProduto.get();

            produto.setName(produtoDTO.name());
            produto.setDescricao(produtoDTO.descricao());
            produto.setQuantidade(produtoDTO.quantidade());
            produto.setPreco(produtoDTO.preco());
            produto.setCategoria(produtoDTO.categoria());

            return produtoRepository.save(produto);
        }
        throw new ProdutoException();
    }

    @Transactional
    public void deleteProduto(Long id) {
        if(produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        }else{
            throw new ProdutoException();
        }
    }

    public List<ProdutoModel> getAllProdutos(){
        return produtoRepository.findAll();
    }

    public List<ProdutoModel> getProdutosByCategoria(String categoria){
        return produtoRepository.findByCategoria(categoria);
    }

    public Set<ProdutoModel> getProdutosByUsuario(Usuario usuario){
        return produtoRepository.findByUsuario(usuario);
    }

    public Optional<ProdutoModel> getProdutosById(Long id){
        return produtoRepository.findById(id);
    }


}

package com.spring.sales_tax_manager.produto;


import com.spring.sales_tax_manager.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Set<Produto> findByUsuario(Usuario usuario);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByPrecoGreaterThan(BigDecimal preco);

    @Query("SELECT p FROM Produto p WHERE p.usuario = :usuario AND p.quantidade > 0")
    Set<Produto> findProdutosDisponiveisByUsuario(@Param("usuario") Usuario usuario);
}

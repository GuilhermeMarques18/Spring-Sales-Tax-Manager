package com.spring.sales_tax_manager.produto;


import com.spring.sales_tax_manager.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long>{
    Set<ProdutoModel> findByUsuarioModel(UsuarioModel usuarioModel);

    List<ProdutoModel> findByCategoria(String categoria);

    List<ProdutoModel> findByPrecoGreaterThan(BigDecimal preco);

}

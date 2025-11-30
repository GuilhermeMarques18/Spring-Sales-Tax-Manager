
package com.spring.sales_tax_manager.vendas;

import com.spring.sales_tax_manager.produto.Produto;
import com.spring.sales_tax_manager.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Set<Venda> findByFuncionario(Usuario funcionario);

    Set<Venda> findByFuncionarioId(Long funcionarioId);

    List<Venda> findByProduto(Produto produto);

    List<Venda> findByDataVendaAfter(LocalDateTime data);

    @Query("SELECT v FROM Venda v WHERE v.imposto.valorCalculado > :valor")
    Set<Venda> findVendasComImpostoAlto(@Param("valor") java.math.BigDecimal valor);
}
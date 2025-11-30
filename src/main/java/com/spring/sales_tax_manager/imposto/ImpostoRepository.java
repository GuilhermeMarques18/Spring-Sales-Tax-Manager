package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.vendas.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long> {

    Optional<Imposto> findByVenda(Venda venda);

    Set<Imposto> findByTipoImposto(String tipoImposto);

    List<Imposto> findByValorCalculadoGreaterThan(BigDecimal valor);

    @Query("SELECT i FROM Imposto i WHERE i.venda.funcionario.id = :funcionarioId")
    Set<Imposto> findImpostosByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
}

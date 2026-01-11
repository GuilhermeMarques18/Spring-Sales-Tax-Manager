package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.vendas.VendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ImpostoRepository extends JpaRepository<ImpostoModel, Long> {

    Optional<ImpostoModel> findByVenda(VendaModel vendaModel);

    List<ImpostoModel> findByTipoImposto(String tipoImposto);

    List<ImpostoModel> findByValorCalculadoGreaterThan(BigDecimal valor);

}

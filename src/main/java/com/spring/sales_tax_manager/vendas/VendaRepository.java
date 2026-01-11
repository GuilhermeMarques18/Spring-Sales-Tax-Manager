package com.spring.sales_tax_manager.vendas;

import com.spring.sales_tax_manager.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface VendaRepository extends JpaRepository<VendaModel, Long> {

    Set<VendaModel> findByFuncionario(UsuarioModel funcionario);

}
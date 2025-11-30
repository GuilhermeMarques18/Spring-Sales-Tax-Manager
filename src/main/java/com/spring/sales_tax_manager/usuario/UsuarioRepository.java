package com.spring.sales_tax_manager.usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface  UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);


    List<Usuario> findByDataCriacao(LocalDate dataCriacao);

    @Query("SELECT u FROM Usuario u WHERE u.role = :role")
    Set<Usuario> findByRole(@Param("role") Role role);


}

package com.spring.sales_tax_manager.usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface  UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmail(String email);
    Optional<UsuarioModel> findByCpf(String cpf);
    List<UsuarioModel> findByDataCriacao(LocalDateTime dataCriacao);

    @Query("SELECT u FROM UsuarioModel u WHERE u.role = :role")
    Set<UsuarioModel> findByRole(@Param("role") Role role);


}

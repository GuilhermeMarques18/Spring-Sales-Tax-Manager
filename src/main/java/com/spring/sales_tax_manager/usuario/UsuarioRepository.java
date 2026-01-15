package com.spring.sales_tax_manager.usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;
import java.util.*;


@Repository
public interface  UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmail(String email);
    Optional<UsuarioModel> findByCpf(String cpf);
    Set<UsuarioModel> findByRole( Role role);
    @Query(value = "SELECT * FROM usuarios", nativeQuery = true)
    List<UsuarioModel> findAllUsuarios();


}

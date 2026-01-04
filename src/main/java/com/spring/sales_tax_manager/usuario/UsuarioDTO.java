package com.spring.sales_tax_manager.usuario;


public record UsuarioDTO(
        String name,
        String email,
        String password,
        String cpf,
        Role role
){}


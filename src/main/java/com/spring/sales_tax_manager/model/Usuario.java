package com.spring.sales_tax_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatorio")
    @Size(min=20, max = 150, message = "O nome deve ter entre 20 e 150 caracteres")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    private String password;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Column(unique=true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Venda> vendas;

}

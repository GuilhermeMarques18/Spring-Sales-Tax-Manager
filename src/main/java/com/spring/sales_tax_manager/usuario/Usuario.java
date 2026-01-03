package com.spring.sales_tax_manager.usuario;

import com.spring.sales_tax_manager.vendas.Venda;
import com.spring.sales_tax_manager.produto.ProdutoModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

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
    @NotBlank(message = " O email é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message =  "Senha é obrigatória")
    @Size(min=8, message = "A senha deeve ter no minimo 8 digitos")
    private String password;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Column(unique=true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoModel> produtoModels;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venda> vendas;

}

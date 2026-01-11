package com.spring.sales_tax_manager.produto;

import com.spring.sales_tax_manager.usuario.UsuarioModel;
import com.spring.sales_tax_manager.vendas.VendaModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produtos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto obrigatorio")
    private String name;

    @NotBlank(message = "Obrigatorio inserir a descriçao do produto")
    @Size(min = 1, max = 150)
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.01", message = "Preço não pode ser nulo ou vazio")
    private BigDecimal preco;

    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private int quantidade;

    private  String categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuarioModel;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<VendaModel> vendaModels;



}

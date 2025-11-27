package com.spring.sales_tax_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Produto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto obrigatorio")
    private String name;

    @NotBlank(message = "Obrigatorio inserir a descriçao do produto")
    @Size(min = 1, max = 150)
    private String descricao;

    @DecimalMin(value = "0.01", message = "Preço não pode ser nulo ou vazio")
    @NotBlank
    private BigDecimal preco;

    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private int quantidade;

    private  String categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Venda> vendas;



}

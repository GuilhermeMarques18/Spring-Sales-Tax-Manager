package com.spring.sales_tax_manager.vendas;

import com.spring.sales_tax_manager.imposto.Imposto;
import com.spring.sales_tax_manager.produto.ProdutoModel;
import com.spring.sales_tax_manager.usuario.UsuarioModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private UsuarioModel funcionario;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoModel produto;

    @Min(value=1, message = "Quantidade deve ser maior ou igual a 1")
    private int quantidade;

    @DecimalMin(value = "0.01")
    private BigDecimal valorVendas;

    @CreationTimestamp
    private LocalDateTime  dataVenda;

    @OneToOne(mappedBy = "venda", cascade = CascadeType.ALL)
    private Imposto imposto;
}

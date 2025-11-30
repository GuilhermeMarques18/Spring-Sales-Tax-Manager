package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.vendas.Venda;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "impostos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Imposto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @DecimalMin(value = "0.0")
    private BigDecimal valorCalculado;

    private String tipoImposto;
}

package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.vendas.VendaModel;
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

public class ImpostoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venda_id")
    private VendaModel venda;

    @DecimalMin(value = "0.0")
    private BigDecimal valorCalculado;

    private String tipoImposto;
}

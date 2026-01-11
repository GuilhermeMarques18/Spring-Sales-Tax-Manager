package com.spring.sales_tax_manager.vendas;


import java.math.BigDecimal;

public record VendaDTO(Long produtoId, int quantidade, BigDecimal valorVendas
) {}
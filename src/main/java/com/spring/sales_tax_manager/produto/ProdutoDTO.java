package com.spring.sales_tax_manager.produto;

import java.math.BigDecimal;

public record ProdutoDTO(
        String name,
        String descricao,
        BigDecimal preco,
        int quantidade,
        String categoria,
        Long usuarioId) {

}

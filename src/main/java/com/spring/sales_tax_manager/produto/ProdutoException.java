package com.spring.sales_tax_manager.produto;

public class ProdutoException extends RuntimeException {
    public ProdutoException() {
        super("Produto não encontrado");
    }
}

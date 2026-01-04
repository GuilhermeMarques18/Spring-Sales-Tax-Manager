package com.spring.sales_tax_manager.usuario;

 class UsuarioException extends RuntimeException {
    public UsuarioException(String message) {
        super("message");
    }
}

 class UsuarioNotFound extends RuntimeException{
    public UsuarioNotFound() {
        super("Usuario não encontrado");
    }
}


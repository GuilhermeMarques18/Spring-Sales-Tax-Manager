package com.spring.sales_tax_manager.usuario.validcpf;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorCpf implements ConstraintValidator<ValidCPF, String> {

    @Override
    public void initialize(ValidCPF constraintAnnotation) {}

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) return false;
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;


        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * pesos1[i];
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) digito1 = 0;
        if (digito1 != (cpf.charAt(9) - '0')) return false;

        soma = 0;
        for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * pesos2[i];
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) digito2 = 0;
        return digito2 == (cpf.charAt(10) - '0');
    }
}
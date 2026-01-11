package com.spring.sales_tax_manager.imposto;

import java.math.BigDecimal;

public record ImpostoDTO(BigDecimal valorCalculado, String tipoImposto) {}
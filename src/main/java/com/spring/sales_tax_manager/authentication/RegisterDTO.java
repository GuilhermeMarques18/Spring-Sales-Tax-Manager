package com.spring.sales_tax_manager.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8)
        String password
) {}

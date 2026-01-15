package com.spring.sales_tax_manager.usuario;

import com.spring.sales_tax_manager.usuario.validcpf.ValidCPF;
import com.spring.sales_tax_manager.vendas.VendaModel;
import com.spring.sales_tax_manager.produto.ProdutoModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")

public class UsuarioModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min=4, max = 150, message = "O nome deve ter entre 20 e 150 caracteres")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank(message = " O email é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message =  "Senha é obrigatória")
    @Size(min=8, message = "A senha deeve ter no minimo 8 digitos")
    private String password;


    @Column(unique=true)
    @ValidCPF
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UsuarioModel(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }



    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "usuarioModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<ProdutoModel> produtoModels;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<VendaModel> vendaModels;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {return password;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.spring.sales_tax_manager.vendas;

import com.spring.sales_tax_manager.imposto.ImpostoService;
import com.spring.sales_tax_manager.produto.ProdutoModel;
import com.spring.sales_tax_manager.produto.ProdutoRepository;
import com.spring.sales_tax_manager.usuario.UsuarioModel;
import com.spring.sales_tax_manager.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ImpostoService impostoService;

    @Transactional
    public VendaModel saveVenda(VendaDTO vendaDTO, String emailFuncionario) {
        ProdutoModel produto = produtoRepository.findById(vendaDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < vendaDTO.quantidade()) {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }
        UsuarioModel funcionario = usuarioRepository.findByEmail(emailFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        BigDecimal valorTotal = produto.getPreco().multiply(BigDecimal.valueOf(vendaDTO.quantidade()));
        produto.setQuantidade(produto.getQuantidade() - vendaDTO.quantidade());
        produtoRepository.save(produto);

        VendaModel venda = new VendaModel();
        venda.setProduto(produto);
        venda.setFuncionario(funcionario);
        venda.setQuantidade(vendaDTO.quantidade());
        venda.setValorVendas(valorTotal);

        venda = vendaRepository.save(venda);
        impostoService.calcularImposto(venda);

        return venda;
    }

    public List<VendaModel> getAllVendas() {
        return vendaRepository.findAll();
    }

    public Optional<VendaModel> getVendaById(Long id) {
        return vendaRepository.findById(id);
    }

    public Set<VendaModel> getVendasByFuncionario(UsuarioModel funcionario) {
        return vendaRepository.findByFuncionario(funcionario);
    }

    @Transactional
    public VendaModel updateVenda(Long id, VendaDTO vendaDTO) {
        Optional<VendaModel> optionalVenda = vendaRepository.findById(id);
        if (optionalVenda.isPresent()) {
            VendaModel venda = optionalVenda.get();
            venda.setQuantidade(vendaDTO.quantidade());
            BigDecimal valorTotal = venda.getProduto().getPreco().multiply(BigDecimal.valueOf(vendaDTO.quantidade()));
            venda.setValorVendas(valorTotal);
            return vendaRepository.save(venda);
        }
        throw new RuntimeException("Venda não encontrada");
    }

    @Transactional
    public void deleteVenda(Long id) {
        if (vendaRepository.existsById(id)) {
            vendaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Venda não encontrada");
        }
    }
}
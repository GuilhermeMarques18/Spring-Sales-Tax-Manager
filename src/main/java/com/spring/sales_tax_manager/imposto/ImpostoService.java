package com.spring.sales_tax_manager.imposto;

import com.spring.sales_tax_manager.vendas.VendaModel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ImpostoService {

    private final ImpostoRepository impostoRepository;

    public ImpostoService(ImpostoRepository impostoRepository) {
        this.impostoRepository = impostoRepository;
    }


    @Transactional
    public ImpostoModel calcularImposto(VendaModel venda) {
        BigDecimal taxa = BigDecimal.valueOf(0.08);
        BigDecimal valorImposto = venda.getValorVendas().multiply(taxa);

        ImpostoModel imposto = new ImpostoModel();
        imposto.setVenda(venda);
        imposto.setValorCalculado(valorImposto);
        imposto.setTipoImposto("ICMS");

        return impostoRepository.save(imposto);
    }

    public List<ImpostoModel> getAllImpostos() {
        return impostoRepository.findAll();
    }

    public Optional<ImpostoModel> getImpostoById(Long id) {
        return impostoRepository.findById(id);
    }

    public List<ImpostoModel> getImpostosByTipo(String tipo) {
        return impostoRepository.findByTipoImposto(tipo);
    }

    @Transactional
    public ImpostoModel updateImposto(Long id, ImpostoDTO impostoDTO) {
        Optional<ImpostoModel> optionalImposto = impostoRepository.findById(id);
        if (optionalImposto.isPresent()) {
            ImpostoModel imposto = optionalImposto.get();
            imposto.setValorCalculado(impostoDTO.valorCalculado());
            imposto.setTipoImposto(impostoDTO.tipoImposto());
            return impostoRepository.save(imposto);
        }
        throw new RuntimeException("Imposto não encontrado");
    }

    @Transactional
    public void deleteImposto(Long id) {
        if (impostoRepository.existsById(id)) {
            impostoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Imposto não encontrado");
        }
    }
}
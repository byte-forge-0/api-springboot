package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.motorpuchase;
import com.example.demo.repository.MotorpuchaseRepository;

@Service
public class CompraService {

    private final MotorpuchaseRepository motorpuchaseRepository;

    public CompraService(MotorpuchaseRepository motorpuchaseRepository) {
        this.motorpuchaseRepository = motorpuchaseRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Map<String, Object>> executarProcessamentoCompras(
            Map<String, Double> pesosAtivos,
            Map<String, Double> cotacoesAtivos) {

        List<motorpuchase> clientesAtivos = motorpuchaseRepository.findByAtivoTrue();
        if (clientesAtivos.isEmpty()) {
            return new HashMap<>();
        }

        double totalInvestir = clientesAtivos.stream()
                .mapToDouble(c -> c.getValorAporteMensal() != null ? c.getValorAporteMensal() / 3.0 : 0.0)
                .sum();

        Map<String, Map<String, Object>> ordens = new HashMap<>();

        if (pesosAtivos == null || cotacoesAtivos == null) {
            return ordens;
        }

        for (Map.Entry<String, Double> entry : pesosAtivos.entrySet()) {
            String ticker = entry.getKey();
            double peso = entry.getValue() != null ? entry.getValue() : 0.0;
            double cotacao = cotacoesAtivos.getOrDefault(ticker, 0.0);

            if (cotacao <= 0.0 || peso <= 0.0) {
                continue;
            }

            double compraCalculada = (totalInvestir * peso) / cotacao;
            int qtdTotal = (int) compraCalculada;

            if (qtdTotal <= 0) {
                continue;
            }
            int qtdLotePadrao = (qtdTotal / 100) * 100;
            int qtdFracionaria = qtdTotal % 100;

            Map<String, Object> detalhesOrdem = new HashMap<>();
            detalhesOrdem.put("ticker", ticker);
            detalhesOrdem.put("totalInvestidoAtivo", totalInvestir * peso);
            detalhesOrdem.put("cotacao", cotacao);
            detalhesOrdem.put("qtdTotal", qtdTotal);
            detalhesOrdem.put("qtdLotePadrao", qtdLotePadrao);
            detalhesOrdem.put("qtdFracionaria", qtdFracionaria);

            ordens.put(ticker, detalhesOrdem);
        }

        return ordens;
    }
}

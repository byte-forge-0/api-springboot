package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.CompraService;

@Component
public class CompraJob {

    private final CompraService compraService;

    public CompraJob(CompraService compraService) {
        this.compraService = compraService;
    }

    @Scheduled(cron = "${app.scheduler.cron:0 0 0 5,15,25 * *}")
    public void executarJob() {
        Map<String, Double> pesosAtivos = new HashMap<>();
        pesosAtivos.put("PETR4", 0.4);
        pesosAtivos.put("VALE3", 0.4);
        pesosAtivos.put("MXRF11", 0.2);

        Map<String, Double> cotacoesAtivos = new HashMap<>();
        cotacoesAtivos.put("PETR4", 30.0);
        cotacoesAtivos.put("VALE3", 80.0);
        cotacoesAtivos.put("MXRF11", 10.0);

        Map<String, Map<String, Object>> ordens = compraService.executarProcessamentoCompras(pesosAtivos, cotacoesAtivos);

        System.out.println("--- Execução do Job de Compras ---");
        for (Map.Entry<String, Map<String, Object>> entry : ordens.entrySet()) {
            Map<String, Object> ordem = entry.getValue();
            System.out.printf("Ativo: %s | Qtd Total: %d (Lote Padrão: %d, Fracionário: %d) | Total Investido: R$ %.2f%n",
                    ordem.get("ticker"),
                    ordem.get("qtdTotal"),
                    ordem.get("qtdLotePadrao"),
                    ordem.get("qtdFracionaria"),
                    ordem.get("totalInvestidoAtivo"));
        }
    }
}

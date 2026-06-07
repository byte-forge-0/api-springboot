package com.example.demo.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.motorpuchase;
import com.example.demo.repository.MotorpuchaseRepository;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private MotorpuchaseRepository motorpuchaseRepository;

    @InjectMocks
    private CompraService compraService;

    @Test
    void testExecutarProcessamentoCompras_Sucesso() {
        motorpuchase client1 = new motorpuchase(1L, 3000.0, true);
        motorpuchase client2 = new motorpuchase(2L, 6000.0, true);

        when(motorpuchaseRepository.findByAtivoTrue()).thenReturn(Arrays.asList(client1, client2));

        Map<String, Double> pesosAtivos = new HashMap<>();
        pesosAtivos.put("PETR4", 0.4);
        pesosAtivos.put("VALE3", 0.6);
        pesosAtivos.put("MXRF11", 0.5);

        Map<String, Double> cotacoesAtivos = new HashMap<>();
        cotacoesAtivos.put("PETR4", 30.0);
        cotacoesAtivos.put("VALE3", 80.0);
        cotacoesAtivos.put("MXRF11", 10.0);

        Map<String, Map<String, Object>> result = compraService.executarProcessamentoCompras(pesosAtivos, cotacoesAtivos);

        assertNotNull(result);
        assertEquals(3, result.size());

        Map<String, Object> ordemPetr = result.get("PETR4");
        assertNotNull(ordemPetr);
        assertEquals(1200.0, ordemPetr.get("totalInvestidoAtivo"));
        assertEquals(30.0, ordemPetr.get("cotacao"));
        assertEquals(40, ordemPetr.get("qtdTotal"));
        assertEquals(0, ordemPetr.get("qtdLotePadrao"));
        assertEquals(40, ordemPetr.get("qtdFracionaria"));

        Map<String, Object> ordemVale = result.get("VALE3");
        assertNotNull(ordemVale);
        assertEquals(1800.0, ordemVale.get("totalInvestidoAtivo"));
        assertEquals(80.0, ordemVale.get("cotacao"));
        assertEquals(22, ordemVale.get("qtdTotal"));
        assertEquals(0, ordemVale.get("qtdLotePadrao"));
        assertEquals(22, ordemVale.get("qtdFracionaria"));

        Map<String, Object> ordemMxrf = result.get("MXRF11");
        assertNotNull(ordemMxrf);
        assertEquals(1500.0, ordemMxrf.get("totalInvestidoAtivo"));
        assertEquals(10.0, ordemMxrf.get("cotacao"));
        assertEquals(150, ordemMxrf.get("qtdTotal"));
        assertEquals(100, ordemMxrf.get("qtdLotePadrao"));
        assertEquals(50, ordemMxrf.get("qtdFracionaria"));
    }

    @Test
    void testExecutarProcessamentoCompras_SemClientesAtivos() {
        when(motorpuchaseRepository.findByAtivoTrue()).thenReturn(Collections.emptyList());

        Map<String, Double> pesosAtivos = new HashMap<>();
        pesosAtivos.put("PETR4", 0.5);

        Map<String, Double> cotacoesAtivos = new HashMap<>();
        cotacoesAtivos.put("PETR4", 30.0);

        Map<String, Map<String, Object>> result = compraService.executarProcessamentoCompras(pesosAtivos, cotacoesAtivos);

        assertTrue(result.isEmpty());
    }

    @Test
    void testExecutarProcessamentoCompras_AtivoComCotacaoInvalida() {
        motorpuchase client = new motorpuchase(1L, 3000.0, true);
        when(motorpuchaseRepository.findByAtivoTrue()).thenReturn(Collections.singletonList(client));

        Map<String, Double> pesosAtivos = new HashMap<>();
        pesosAtivos.put("INVALID", 0.5);

        Map<String, Double> cotacoesAtivos = new HashMap<>();
        cotacoesAtivos.put("INVALID", 0.0);


        Map<String, Map<String, Object>> result = compraService.executarProcessamentoCompras(pesosAtivos, cotacoesAtivos);

        assertTrue(result.isEmpty());
    }
}

package com.example.demo.config;

import com.example.demo.service.CompraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraJobTest {

    @Mock
    private CompraService compraService;

    @InjectMocks
    private CompraJob compraJob;

    @Test
    void testExecutarJob() {
        // Arrange
        Map<String, Map<String, Object>> mockOrdens = new HashMap<>();
        Map<String, Object> ordemPetr = new HashMap<>();
        ordemPetr.put("ticker", "PETR4");
        ordemPetr.put("qtdTotal", 40);
        ordemPetr.put("qtdLotePadrao", 0);
        ordemPetr.put("qtdFracionaria", 40);
        ordemPetr.put("totalInvestidoAtivo", 1200.0);
        mockOrdens.put("PETR4", ordemPetr);

        when(compraService.executarProcessamentoCompras(anyMap(), anyMap())).thenReturn(mockOrdens);

        // Act
        compraJob.executarJob();

        // Assert
        verify(compraService, times(1)).executarProcessamentoCompras(anyMap(), anyMap());
    }
}

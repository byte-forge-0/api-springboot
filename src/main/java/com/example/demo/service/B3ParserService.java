package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cotacao;
import com.example.demo.repository.CotacaoRepository;

@Service
public class B3ParserService {

    @Autowired
    private CotacaoRepository repository;

    public void processarArquivos(String pasta) throws IOException {
        Files.list(Paths.get(pasta))
             .filter(path -> path.toString().endsWith(".txt"))
             .forEach(this::processarArquivo);
    }

    private void processarArquivo(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("0") || linha.startsWith("9")) continue; 

                try {
                    parseLinha(linha);
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLinha(String linha) {
        String ticker = linha.substring(12, 23).trim();
        double preco = Double.parseDouble(linha.substring(109, 121).trim()) / 100.0;

        Cotacao cotacao = new Cotacao(ticker, preco);
        repository.save(cotacao);
    }
}
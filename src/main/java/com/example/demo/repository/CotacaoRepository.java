package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cotacao;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long>{
    
}

package com.example.demo.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class motorpuchase {
    @Id
    private Long id;

    @Column(name = "valorAporteMensal")
    private Double valorAporteMensal;

    @Column(name = "ativo")
    private Boolean ativo;
}

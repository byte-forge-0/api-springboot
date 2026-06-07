package com.example.demo.repository;

import com.example.demo.model.motorpuchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorpuchaseRepository extends JpaRepository<motorpuchase, Long> {
    List<motorpuchase> findByAtivoTrue();
}

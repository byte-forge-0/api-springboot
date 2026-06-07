package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MotorpurchaseTest {

    @Test
    void testGettersSettersAndConstructors() {
        motorpuchase m1 = new motorpuchase();
        m1.setId(10L);
        m1.setValorAporteMensal(450.0);
        m1.setAtivo(true);

        assertEquals(10L, m1.getId());
        assertEquals(450.0, m1.getValorAporteMensal());
        assertTrue(m1.getAtivo());

        motorpuchase m2 = new motorpuchase(20L, 900.0, false);
        assertEquals(20L, m2.getId());
        assertEquals(900.0, m2.getValorAporteMensal());
        assertFalse(m2.getAtivo());
    }
}

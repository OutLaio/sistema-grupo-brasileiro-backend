package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MeasurementsRepositoryTest {

    @Autowired
    private MeasurementsRepository measurementsRepository;

    @Test
    public void contextLoads() {
        assertNotNull(measurementsRepository, "MeasurementsRepository should be properly injected");
    }
}

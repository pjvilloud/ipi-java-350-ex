package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceIntegrationMockTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    void testCalculPerformanceCommerciale() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 1000;
        long obj = 1000;

        e.setMatricule("C00123");
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(employeRepository.findByMatricule("C00123")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);

        //When
        employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(1);
    }
}
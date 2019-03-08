package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeServiceIntegrationMockTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    //#region integrationMockTestPerformanceCommerciale()
    @Test
    public void integrationMockCalculPerformanceCommerciale() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 1000;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(matricule)).thenReturn(e);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);

        //When
       this.employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(this.employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(1);
    }

    @Test
    public void integrationMockCalculPerformanceCommercialePlus10() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 1100;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(matricule)).thenReturn(e);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);

        //When
       this.employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(this.employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(2);
    }

    @Test
    public void integrationMockCalculPerformanceCommercialePlus30() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 1300;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(matricule)).thenReturn(e);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);

        //When
       this.employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(this.employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(6);
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeMoins20() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 800;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(matricule)).thenReturn(e);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);

        //When
       this.employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(this.employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(1);
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeMoins30() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 700;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(matricule)).thenReturn(e);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);

        //When
       this.employeService.calculPerformanceCommercial(matricule,ca,obj);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(this.employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(1);
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeCaObjNull() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 7000;
        Long obj = null;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        //When
        Throwable throwable = Assertions.catchThrowable(() -> this.employeService.calculPerformanceCommercial(matricule,ca,obj));

        // Then
        Assertions.assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeCaNull() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        Long ca = null;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        //When
        Throwable throwable = Assertions.catchThrowable(() -> this.employeService.calculPerformanceCommercial(matricule,ca,obj));

        // Then
        Assertions.assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeMatriculeFaux() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "T00123";
        long ca = 7000;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        //When
        Throwable throwable = Assertions.catchThrowable(() -> this.employeService.calculPerformanceCommercial(matricule,ca,obj));

        // Then
        Assertions.assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Le matricule ne peut être null et doit commencer par un C !");
    }

    @Test
    public void integrationMockCalculPerformanceCommercialeEmployePasEnBase() throws EmployeException {
        //Given
        Employe e = new Employe();
        String matricule = "C00123";
        long ca = 700;
        long obj = 1000;

        e.setMatricule(matricule);
        e.setPerformance(1);
        e.setTempsPartiel(1D);

        Mockito.when(this.employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);

        //When
        Throwable throwable = Assertions.catchThrowable(() -> this.employeService.calculPerformanceCommercial(matricule,ca,obj));

        // Then
        Assertions.assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Le matricule " + matricule + " n'existe pas !");
    }
    //#endregion
}
package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    public void test_WhenMatriculeNull(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial(null,5l,5l);
        });

        //Then
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",thrown.getMessage());
    }

    @Test
    public void test_WhenMatriculeNotOk(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("B123",5l,5l);
        });

        //Then
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",thrown.getMessage());
    }

    @Test
    public void test_WhencaTraiteNull(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",null,5l);
        });

        //Then
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",thrown.getMessage());
    }

    @Test
    public void test_WhencaTraiteNegative(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",-5l,5l);
        });

        //Then
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",thrown.getMessage());
    }

    @Test
    public void test_WhenobjectifCaNull(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",5l,null);
        });

        //Then
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",thrown.getMessage());
    }

    @Test
    public void test_WhenobjectifCaNegative(){
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",5l,-5l);
        });

        //Then
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",thrown.getMessage());
    }

    @Test
    public void test_WhenMatriculeNotExist(){
        //Given
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(null);
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",0l,1l);
        });

        //Then
        Assertions.assertEquals("Le matricule C123 n'existe pas !",thrown.getMessage());
    }

    @Test
    public void test_WhenCas1_PerformanceMoyenneNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(5);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",6l,10l);
        //Then
        Assertions.assertEquals(1,employe.getPerformance());
    }

    @Test
    public void test_WhenCas1_andPerformanceMoyenneInf() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(0d);
        //When
        this.employeService.calculPerformanceCommercial("C123",6l,10l);
        //Then
        Assertions.assertEquals(2,employe.getPerformance());
    }

    @Test
    public void test_WhenCas2_PerformaneBaseSup_andPerformanceMoyenneNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",8l,10l);
        //Then
        Assertions.assertEquals(1,employe.getPerformance());
    }

    @Test
    public void test_WhenCas2_PerformaneBaseInf_andPerformanceMoyenneInf() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",8l,10l);
        //Then
        Assertions.assertEquals(3,employe.getPerformance());
    }

    @Test
    public void test_WhenCas3_PerformaneBaseSup_andPerformanceMoyenneNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(0);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",10l,10l);
        //Then
        Assertions.assertEquals(1,employe.getPerformance());
    }

    @Test
    public void test_WhenCas3_PerformaneBaseInf_andPerformanceMoyenneInf() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",10l,10l);
        //Then
        Assertions.assertEquals(5,employe.getPerformance());
    }

    @Test
    public void test_WhenCas4_PerformaneBaseSup_andPerformanceMoyenneNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",12l,10l);
        //Then
        Assertions.assertEquals(3,employe.getPerformance());
    }

    @Test
    public void test_WhenCas4_PerformaneBaseInf_andPerformanceMoyenneInf() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",12l,10l);
        //Then
        Assertions.assertEquals(6,employe.getPerformance());
    }

    @Test
    public void test_WhenCas5_PerformaneBaseSup_andPerformanceMoyenneNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",13l,10l);
        //Then
        Assertions.assertEquals(6,employe.getPerformance());
    }

    @Test
    public void test_WhenCas5_PerformaneBaseInf_andPerformanceMoyenneInf() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(employe);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",13l,10l);
        //Then
        Assertions.assertEquals(9,employe.getPerformance());
    }
}
package com.ipiecoles.java.java350.tests;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    public void testMatriculeEqualNull(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial(null,5l,5l);
        });

        //Then
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",employeException.getMessage());
    }

    @Test
    public void testMatriculeNotEqualOk(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("B123",5l,5l);
        });

        //Then
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",employeException.getMessage());
    }

    @Test
    public void testCATraiteEqualNull(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",null,5l);
        });

        //Then
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",employeException.getMessage());
    }

    @Test
    public void testCATraiteIsNegative(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",-5l,5l);
        });

        //Then
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",employeException.getMessage());
    }

    @Test
    public void testObjectifCAEqualNull(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",5l,null);
        });

        //Then
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",employeException.getMessage());
    }

    @Test
    public void testObjectifCAIsNegative(){
        //When
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->{
            this.employeService.calculPerformanceCommercial("C123",5l,-5l);
        });

        //Then
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",employeException.getMessage());
    }

    @Test
    public void testMatriculeNotExist(){
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
    public void testCas1PerformanceMoyenneEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(5);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",6l,10l);
        //Then
        Assertions.assertEquals(1,e1.getPerformance());
    }

    @Test
    public void testCas1andPerformanceMoyenneIsInf() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(0d);
        //When
        this.employeService.calculPerformanceCommercial("C123",6l,10l);
        //Then
        Assertions.assertEquals(2,e1.getPerformance());
    }

    @Test
    public void testCas2PerformanceBaseIsSupAndPerformanceMoyenneEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",8l,10l);
        //Then
        Assertions.assertEquals(1,e1.getPerformance());
    }

    @Test
    public void testCas2PerformanceBaseIsInfAndPerformanceMoyenneIsInf() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",8l,10l);
        //Then
        Assertions.assertEquals(3,e1.getPerformance());
    }

    @Test
    public void testCas3PerformanceBaseIsSupAndPerformanceMoyenneEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(0);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",10l,10l);
        //Then
        Assertions.assertEquals(1,e1.getPerformance());
    }

    @Test
    public void testCas3PerformanceBaseIsInfAndPerformanceMoyenneIsInf() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",10l,10l);
        //Then
        Assertions.assertEquals(5,e1.getPerformance());
    }

    @Test
    public void testCas4PerformanceBaseIsSupAndPerformanceMoyenneEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",12l,10l);
        //Then
        Assertions.assertEquals(3,e1.getPerformance());
    }

    @Test
    public void testCas4PerformanceBaseIsInfAndPerformanceMoyenneIsInf() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",12l,10l);
        //Then
        Assertions.assertEquals(6,e1.getPerformance());
    }

    @Test
    public void testCas5PerformanceBaseIsSupAndPerformanceMoyenneEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(2);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        //When
        this.employeService.calculPerformanceCommercial("C123",13l,10l);
        //Then
        Assertions.assertEquals(6,e1.getPerformance());
    }

    @Test
    public void testCas5PerformanceBaseIsInfAndPerformanceMoyenneIsInf() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setPerformance(4);
        Mockito.when(this.employeRepository.findByMatricule("C123")).thenReturn(e1);
        Mockito.when(this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        //When
        this.employeService.calculPerformanceCommercial("C123",13l,10l);
        //Then
        Assertions.assertEquals(9,e1.getPerformance());
    }
}
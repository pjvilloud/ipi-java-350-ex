package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    private org.junit.jupiter.api.Assertions Assertion;

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    //Verification enregistrement avec un cas nominal
    @Test
    public void integrationTestCalculPerformanceCommercialSave() throws EmployeException {
        //Given
        String nom = "Dumas";
        String prenom = "Jean";
        String matricule = "C12345";
        Long caTraite = 4800L;
        Long objectifCa = 5100L;
        Integer performance = 5;
        Employe employe = new Employe(nom, prenom, matricule, LocalDate.now(), 1500.0, 6, 1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertion.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertion.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertion.assertEquals(matricule, employeArgumentCaptor.getValue().getMatricule());
        Assertion.assertEquals(performance, employeArgumentCaptor.getValue().getPerformance());
        Assertion.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    public void integrationTestAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        employeRepository.save(new Employe("Demaison", "Aurelien", "C15354", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Dujardin", "Guillaume", "C56548", LocalDate.now(), Entreprise.SALAIRE_BASE, 7, 1.0));
        employeRepository.save(new Employe("Dumas", "Eric", "T16598", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Dupond", "Yannis", "T62453", LocalDate.now(), Entreprise.SALAIRE_BASE, 7, 1.0));
        employeRepository.save(new Employe("Dubateau", "Franck", "T15684", LocalDate.now(), Entreprise.SALAIRE_BASE, 11, 1.0));

        //When
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        //Then
        Assertions.assertThat(avgPerformance).isEqualTo(7);
    }

}

package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.Arrays;

import static com.ipiecoles.java.java350.model.NiveauEtude.INGENIEUR;
import static com.ipiecoles.java.java350.model.Poste.COMMERCIAL;

@RunWith(MockitoJUnitRunner.class)
public class EmployeSeviceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    /*@Test
    public void embaucheEmployeTest() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = COMMERCIAL;
        NiveauEtude niveauEtude = INGENIEUR;
        Double tempPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempPartiel);

        //Then
        Employe employe = employeRepository.findAll().get(0);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }*/

    /*@Test
    public void embaucheEmployeTest() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = COMMERCIAL;
        NiveauEtude niveauEtude = INGENIEUR;
        Double tempPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempPartiel);

        //Then
        Mockito.verify(employeRepository.findByMatricule("T0346"));
        Mockito.verify(employeRepository, Mockito.never()).avgPerformanceWhereMatriculeStartsWith(Mockito.anyString());
        Employe employe = employeRepository.findAll().get(0);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }*/

    @Test
    public void embaucheEmployeTest() throws EmployeException {
        this.setup();
        logger.info("EmbaucheEmployeTest");

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.INGENIEUR;
        Double tempPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("00346")).thenReturn(new Employe());

        logger.error("Coucou les loulous");
        //When
        Throwable exception = Assertions.catchThrowable(() ->
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempPartiel));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EntityExistsException.class);
    }
}

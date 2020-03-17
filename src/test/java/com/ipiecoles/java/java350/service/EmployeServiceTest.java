package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @Mock
    EmployeRepository employeRepository;
    @InjectMocks
    EmployeService employeService;

    @ParameterizedTest()
    // performance , caTraite , objectifCa, performanceMoyenne , resultatPerformance

    @CsvSource({
            "2, 70, 100, 1.00, 1",
            "10, 90, 100, 1.00, 9",
            "10, 99, 100, 1.00, 11",
            "10, 110, 100, 1.00, 12",
            "10, 1500, 100, 1.00, 15"
    })

    public void testCalculPerformenceCommercial(Integer performance, Long caTraite,
                                                Long objectifCa, Double performanceMoyenne,
                                                Integer resultatPerformance) throws EmployeException {
        //Given
        String matricule = "C54321";
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyenne);

        //WHEN
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //THEN
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1))
                .save(employeCaptor.capture());

        Employe employeCaptorValue = employeCaptor.getValue();
        Assertions.assertThat(employeCaptorValue.getPerformance()).isEqualTo(resultatPerformance);
    }

    // Tests mockés ----------> Embauche
    @Test
    public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        // Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        // ouMockito.verify(employeRep, Mockito.never).save(employeCaptor.capture());

        Employe employeCaptorValue = employeCaptor.getValue();
        Assertions.assertThat(employeCaptorValue.getNom()).isEqualTo(nom);
        Assertions.assertThat(employeCaptorValue.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employeCaptorValue.getMatricule()).isEqualTo("C00346");
        Assertions.assertThat(employeCaptorValue.getDateEmbauche()).isEqualTo(LocalDate.now());
        // ou
        //Assertions.assertThat(employeCaptor.getValue().getDateEmbauche().format(
        //		DateTimeFormatter.ofPattern("yyyyMMdd"))).isEqualTo(LocalDate.now().format(
        //				DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertThat(employeCaptorValue.getTempsPartiel()).isEqualTo(tempsPartiel);
        // Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employeCaptorValue.getPerformance()).isEqualTo(1);
        // salaire de base 1521 * 1.2 * 1.0 = 1825.46
        Assertions.assertThat(employeCaptorValue.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    public void testEmbaucheEmployeLimiteMatricule() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        // When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Aurait dû planter!");
        } catch (Exception e) {
            // Then
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }

}
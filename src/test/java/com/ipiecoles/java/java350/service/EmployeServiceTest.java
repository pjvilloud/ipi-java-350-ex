package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import io.cucumber.java8.Ar;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye0Employe() throws EmployeException {

        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
        // Given

        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);

        Mockito.when(employeRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        // Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("M45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());

        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);

    }

    void testExceptionNormal() {
        try {
            employeService.embaucheEmploye(null, null, null, null, null);
            Assertions.fail("Aurait du lancer une exception");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Message de l'erreur");
        }
    }

    void testExceptionJava8() {
        //Given
        Assertions.assertThatThrownBy(() -> {
            //When
            employeService.embaucheEmploye(null, null, null, null, null);
        })//Then
                .isInstanceOf(EmployeException.class).hasMessage("Message de l'erreur");
    }

    @Test
    void testEmbaucheEmployeLimiteMatricule() {
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
        try {
            employeService.embaucheEmploye("Doe","John", Poste.MANAGER, NiveauEtude.BTS_IUT, 1.0);
        } catch (EmployeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }



}
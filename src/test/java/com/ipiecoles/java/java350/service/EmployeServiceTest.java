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
class EmployeServiceTest {
    @Mock
    EmployeRepository employeRepository;
    @InjectMocks
    EmployeService employeService;

    @Test
    void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

//WHEN
        employeService.embaucheEmploye(nom, prenom, poste,
                niveauEtude, tempsPartiel);

// THEN
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1))
                .save(employeCaptor.capture());

        Employe employe = employeCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        //1521*1.2*1
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    public void testEmbaucheEmployeLimiteMatricule() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        // je simule que la base arrive à terme
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        // When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Aurait dû planter!"); // fail fait échourer le test qd même et signale que ça aurait du planter
        } catch (Exception e) {
            // Then
            // je fais en sorte que j'ai bien une exception avec le try catch dans when
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }
}
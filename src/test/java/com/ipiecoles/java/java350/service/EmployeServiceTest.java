package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {
    @Mock
    EmployeRepository employeRepository;
    @InjectMocks
    EmployeService employeService;
    @ParameterizedTest()
    //performance,caTraite, objectifCa, performanceMoyenne,  resultatPerformance
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
        //String matricule="C22222";
        //Double performance=10.0;
        //Long caTraite=10000L;
        //Long objectifCa=50000L;
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyenne);

        //WHEN
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //THEN
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1))
                .save(employeCaptor.capture());

        Employe e = employeCaptor.getValue();
        //Assertions.assertThat(employe.getMatricule()).isEqualTo("C22222");
        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(resultatPerformance);
    }

    @Test
    public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        // on donne les cas car on n'a pas de BDD donc les données sont apportées avec when
        // cas nominal, findLastMatricule => 00345 / null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        // findByMatricule => null / pensez à incrémenter d'1 par rapport au test du dessus 00345, et je veux que ça renvoi null
        Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);


        // Then
        // BDD (simulée) si l'employé est bien créé (nom, prenom, matricule, salaire, date embauche, performance, temps partiel)

        // au moment où on appele save, je récupère les valeur d'employe car je ne peux pas enregistrer en BDD, voir Mock2 cours
        // initialisation des capteurs d'argument
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);

        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        // ou
        // Mockito.verify(employeRep).save(employeCaptor.capture());
        // ou si on n'est pas passé
        // Mockito.verify(employeRep, Mockito.never).save(employeCaptor.capture());

        Employe employe = employeCaptor.getValue();

        // Employe employeVerif = new Employe(nom, prenom..);
        // Assertions.assertThat(employe).isEqualTo(employeVerif);

        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");

        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        // ou
        //Assertions.assertThat(employeCaptor.getValue().getDateEmbauche().format(
        //		DateTimeFormatter.ofPattern("yyyyMMdd"))).isEqualTo(LocalDate.now().format(
        //				DateTimeFormatter.ofPattern("yyyyMMdd")));

        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);

        // Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);

        // salaire de base 1521 * 1.2 * 1.0 = 1825.46
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
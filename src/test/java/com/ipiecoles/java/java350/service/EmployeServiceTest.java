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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.ipiecoles.java.java350.model.NiveauEtude.INGENIEUR;
import static com.ipiecoles.java.java350.model.Poste.COMMERCIAL;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }


    /*@DisplayName("Test calcul performance commercial mauvais paramêtre")
    @ParameterizedTest
    @CsvSource({
            "'C12345', 'null', '5000L', 'Le chiffre d'affaire traité ne peut être négatif ou null !'", // Cas où le chiffre d'affaire traité est égal à null.
            "'C12345', '4600L', 'null', 'L\'objectif de chiffre d'affaire ne peut être négatif ou null !'", // Cas où l'objectif du chiffre d'affaire est égal à null.
            "'T12345', '4800L', '5000L', 'Le matricule ne peut être null et doit commencer par un C !'" // Cas ou le matricule renseigner n'est pas bon (ne commence pas par un C).
    })
    @Test
    public void testCalculPerformanceCommercialMauvaisParam(String matricule, String caTraite, String objectifCa, String exceptionMessage) {
        //Given
        System.out.println("matricule = " + matricule);
        System.out.println("matricule = " + matricule.getClass().getName());
        System.out.println("caTraite = " + caTraite);
        System.out.println("matricule = " + ((Object) caTraite).getClass().getName());
        System.out.println("objectifCa = " + objectifCa);
        System.out.println("matricule = " + ((Object) objectifCa).getClass().getName());
        System.out.println("exceptionMessage = " + exceptionMessage);
        System.out.println("matricule = " + exceptionMessage.getClass().getName());
        if (caTraite != "null") {
            long caTraite2 = Long.parseLong(caTraite);
        } else
            String caTraite2 = Long.parseLong(null);
        }
        long objectifCa2 =Long.parseLong(objectifCa);

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo(exceptionMessage);
    }*/

    // Cas ou l'employé n'est pas trouvé.
    /*@Test
    public void testCalculPerformanceCommercialEmployeNExistePas(){
        //Given
        String matricule = "C9999";
        Long caTraite = 4800L;
        Long objectifCa = 5000L;
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le matricule " + matricule + " n'existe pas !");
    }*/

    // ==== dupli ======//

    @Test
    public void testCalculPerformanceCommercialMauvaisCaTraite(){
        //Given
        Long caTraite = null;
        String matricule = "C12345";
        Long objectifCa = 5000L;

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    // Cas où l'objectif du chiffre d'affaire est égal à null.
    @Test
    public void testCalculPerformanceCommercialMauvaisObjectifCa(){
        //Given
        String matricule = "C12345";
        Long caTraite = 4600L;
        Long objectifCa = null;

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    // Cas ou le matricule renseigner n'est pas bon (ne commence pas par un C).
    @Test
    public void testCalculPerformanceCommercialMauvaisMatricule(){
        //Given
        String matricule = "T12345";
        Long caTraite = 4800L;
        Long objectifCa = 5000L;

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
    }

    // Cas ou l'employé n'est pas trouvé.
    @Test
    public void testCalculPerformanceCommercial(){
        //Given
        String matricule = "C9999";
        Long caTraite = 4800L;
        Long objectifCa = 5000L;
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le matricule " + matricule + " n'existe pas !");
    }

    // Cas 2 où employe.getPerformance() > Entreprise.PERFORMANCE_BASE;
    @Test
    public void testCalculPerformanceCommercialCas2AvecPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(6);
        String matricule = "C12345";
        Long caTraite = 4800L;
        Long objectifCa = 5100L; //4800 >= 4080 (5100L*0.8) && 4800 < 4845 (5100L*0.95)
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(4); //performance = employe.getPerformance() (6) - 2
    }

    // Cas 2 où Entreprise.PERFORMANCE_BASE > employe.getPerformance();
    @Test
    public void testCalculPerformanceCommercialCas2SansPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(0);
        String matricule = "C12345";
        Long caTraite = 4800L;
        Long objectifCa = 5100L; //4800 >= 4080 (5100L*0.8) && 4800 < 4845 (5100L*0.95)
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); //performance = Entreprise.PERFORMANCE_BASE
    }

    // Cas 3 où employe.getPerformance() > Entreprise.PERFORMANCE_BASE;
    @Test
    public void testCalculPerformanceCommercialCas3AvecPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(16);
        String matricule = "C12345";
        Long caTraite = 1867L;
        Long objectifCa = 1950L; //1867 >= 1852 (1950*0.95) && 1867 <= 2047 (1950*1.05)
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(16); //performance = employe.getPerformance() (16)
    }

    // Cas 3 où Entreprise.PERFORMANCE_BASE > employe.getPerformance();
    @Test
    public void testCalculPerformanceCommercialCas3SansPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(0);
        String matricule = "C12345";
        Long caTraite = 1867L;
        Long objectifCa = 1950L; //1867 >= 1852 (1950*0.95) && 1867 <= 2047 (1950*1.05)
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); //performance = Entreprise.PERFORMANCE_BASE
    }

    // Cas 4 où employe.getPerformance() > Entreprise.PERFORMANCE_BASE;
    @Test
    public void testCalculPerformanceCommercialCas4AvecPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(9);
        String matricule = "C12345";
        Long caTraite = 12569L;
        Long objectifCa = 11000L; //12569 <= (11000*1.2) 13200 && 12569 > (11000*1.05) 11500
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(10); //performance = employe.getPerformance() (9) + 1
    }

    // Cas 4 où Entreprise.PERFORMANCE_BASE > employe.getPerformance();
    @Test
    public void testCalculPerformanceCommercialCas4SansPerformance() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(0);
        String matricule = "C12345";
        Long caTraite = 12569L;
        Long objectifCa = 11000L; //12569 <= (11000*1.2) 13200 && 12569 > (11000*1.05) 11500
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); //performance = Entreprise.PERFORMANCE_BASE
    }

    // Cas 5
    @Test
    public void testCalculPerformanceCommercialCas5() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(45);
        String matricule = "C12345";
        Long caTraite = 26357L;
        Long objectifCa = 15000L; //26357 > (15000*1.2) 18000
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(49); //performance = employe.getPerformance() + 4;
    }

    //Si autre cas, on reste à la performance de base.
    @Test
    public void testCalculPerformanceCommercialAutreCas() throws EmployeException {
        //Given
        Employe employe = new Employe();
        String matricule = "C12345";
        Long caTraite = 12L;
        Long objectifCa = 2200L;
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); //performance = Entreprise.PERFORMANCE_BASE
    }

    //Calcul de la performance moyenne
    @Test
    public void testCalculPerformanceCommercialMoyenne() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(6);
        String matricule = "C12345";
        Long caTraite = 4800L;
        Long objectifCa = 5100L; //Cas 2
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(Double.valueOf(2L)); // performance (6) > performanceMoyenne (2)

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(5); //performance = employe.getPerformance() (6) - 2 + performance++
    }


    /*

    //Calcul de la performance moyenne
    Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        if(performanceMoyenne != null && performance > performanceMoyenne){
        performance++;
    }

    //Affectation et sauvegarde
        employe.setPerformance(performance);
        employeRepository.save(employe);
        */


    //============================TD ========================================//
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

    /*@Test
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
    }*/
}

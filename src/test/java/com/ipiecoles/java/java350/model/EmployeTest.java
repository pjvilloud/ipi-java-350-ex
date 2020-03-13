package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNull() {
        //Cas où la date d'acienneté est null
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNowPlus2y() {
        //Cas où la date d'embauche est > à auj + 2 ans
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNowMinus3y() {
        //Cas où la date d'embauche est > à auj - 3 ans
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(3);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNow() {
        //Cas où la date d'embauche est = auj
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @ParameterizedTest(name = "Employé matricule {0}, {1} années d ancienneté, {2}, {3} gagnera une prime de {4}")
    @CsvSource({
            "T12345, 0, 1.0, 1, 1000",
            "T12345, 0, 0.5, 1, 500.0"
    })
    void testGetPrimeAnnuelle(String matricule, Integer nbAnneeAnciennete, Double tempPartiel, Integer performance, Double primeFinale) {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setTempsPartiel(tempPartiel);
        employe.setPerformance(performance);

        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertThat(prime).isEqualTo(primeFinale);
    }

    @Test
    void testGetPrimeAnnuelle() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T12345");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(Entreprise.primeAnnuelleBase());
    }

    ////////////////////////////////
    // TU PASSAGE A TEMPS PARTIEL //
    ////////////////////////////////

    @Test
    void testPassageTempsPartiel() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T12345");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setSalaire(1000d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
//        Employe employe1 = employe.passageTempsPartiel();

        //Then
//        Assertions.assertThat(employe1.getTempsPartiel()).isLessThan(1d);
//        Assertions.assertThat(employe1.getSalaire()).isEqualTo(500d);

    }

    ////////////////
    // EVALUATION //
    ////////////////
    @Test
    public void testAugmenterSalairePourcentageNegatif() throws EmployeException{
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T00001");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setSalaire(1000d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Throwable exception = Assertions.catchThrowable(() ->
                employe.augmenterSalaire(-20));

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000d);
        Assertions.assertThat(exception).isInstanceOf(Exception.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Vous ne pouvez pas diminuer le salaire dans ce contexte!");
    }

    @Test
    public void testAugmenterSalairePourcentage20() throws Exception {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T00001");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setSalaire(1233d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        employe.augmenterSalaire(13);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1393.29);
    }

    @Test
    public void testAugmenterSalairePourcentageNul() throws Exception {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T00001");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setSalaire(1000d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        employe.augmenterSalaire(0);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000d);
    }

    @ParameterizedTest(name = "Pour l année {0}, l employé de matricule {1}, travaillant à {3} aura {4} jours de RTT")
    @CsvSource({
            "2019-01-21, T12345, 1.0, 'temps plein', 8",
            "2020-05-16, M12345, 0.5, 'mi-temps', 5",
            "2021-03-11, M19587, 1, 'temps plein', 10",
            "2022-07-02, T22037, 0.5, 'mi-temps', 5",
            "2032-12-02, T28974, 1, 'temps plein', 11",
            "2044-10-10, C58166, 0.5, 'mi-temps', 5"
    })
    public void testGetNbRtt(LocalDate date, String matricule, Double tempsPartiel, String tempPartielStr, Integer nbRtt) {
        //Given
        LocalDate localDate = date;

        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Integer nbJoursRTT = employe.getNbRtt(localDate);

        //Then
        Assertions.assertThat(nbJoursRTT).isEqualTo(nbRtt);
    }

    @ParameterizedTest(name = "Employé de matricule {0}, travaillant à {2} aura {3} jours de RTT cette année")
    @CsvSource({
            "T12345, 1.0, 'temps plein', 10",
            "M12345, 0.5, 'mi-temps', 5",
    })
    public void testGetNbRttNoDate(String matricule, Double tempsPartiel, String tempsPartielStr, Integer nbRtt) {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Integer nbJoursRTT = employe.getNbRtt();

        //Then
        Assertions.assertThat(nbJoursRTT).isEqualTo(nbRtt);
    }
}

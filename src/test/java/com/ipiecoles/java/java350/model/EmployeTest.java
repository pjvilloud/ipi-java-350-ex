package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }

    @Test
    public void passageTempsPartiel(){
        // given
        Employe e = new Employe();
        e.setTempsPartiel(1.0);

        // when
        e.passeTempsPartiel();

        // Then
        Assertions.assertEquals(0.5, e.getTempsPartiel());
    }

    // une augmentation de salaire de 1%
    @Test
    public void augmenterSalaire1Pourcent() throws EmployeException {
        // GIVEN
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // WHEN
        e.augmenterSalaire(1);
        // THEN
        Assertions.assertEquals(1515.0,e.getSalaire());
    }

    // Une augmentation de 100%
    @Test
    public void augmenterSalaire100Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
        e.augmenterSalaire(100);
        // then
        Assertions.assertEquals(3000.0,e.getSalaire());
    }

    // Une augmentation de plus de 100% throw une erreur, et le salaire ne bouge pas
    @Test
    public void augmenterSalaire150Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        e.setMatricule("E00001");
        // when
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->
            e.augmenterSalaire(150)
        );
        // then
        Assertions.assertEquals("Attention, augmentation ou diminution de plus de 100% effectuée le: "
                + LocalDate.now() + " pour l'employe de matricule E00001", employeException.getMessage());

        Assertions.assertEquals(1500.0,e.getSalaire());
    }

    // Une "augmentation" d'un pourcentage négatif -> = baisse de salaire, autorisé.
    @Test
    public void augmenterSalaireMinus50Pourcent() throws EmployeException {

        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
                e.augmenterSalaire(-50);
        // then
        Assertions.assertEquals(750.0,e.getSalaire());
    }

    // Une "augmentation" de 0% -> retourne un message d'erreur
    @Test
    public void augmenterSalaire0Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->
                e.augmenterSalaire(0)
        );
        // then
        Assertions.assertEquals("Attention, pas de modifications de salaire", employeException.getMessage());
        Assertions.assertEquals(1500.0,e.getSalaire());
    }


//    Tester unitairement (en utilisant les tests paramétrés) la méthode getNbRtt d'Employe. Le nombre de RTT se calcule à partir de la formule suivante : Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps - Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end - Nombre de congés payés. Le tout au pro-rata du taux d'activité du salarié. Attention, des erreurs sont présentes dans cette méthode. Faites donc vos calculs avant et débugguer votre code pour trouver les erreurs. Aidez-vous de Sonar... Rendre cette méthode plus propre, documentée et lisible. Infos :
//
//            2019 : l'année est non bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
//            2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
//            2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
//            2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.
    @ParameterizedTest
    @ValueSource(strings = {"2019-01-01", "2021-01-01", "2022-01-01", "2032-01-01"})
    public void nombreRttTest(String dateString ) {
        // given
        // an employe embauché au jour même du calcul
        Employe employe = new Employe();
        LocalDate localDate = LocalDate.parse(dateString);
        employe.setDateEmbauche(localDate);
        // when la date passée en param est la même date
        Integer nbRtt = employe.getNbRtt(localDate);
        // then
        switch (localDate.toString()) {
            case "2019-01-01":
                Assertions.assertEquals(8, nbRtt);
                break;
            case "2021-01-01":
                Assertions.assertEquals(11, nbRtt);
                break;
            case "2022-01-01":
                Assertions.assertEquals(10, nbRtt);
                break;
            case "2032-01-01":
                Assertions.assertEquals(12, nbRtt);
                break;
        }

    }

    // le test va prendre une date en valeur, une par année, il ira ensuite créer un employé,
    // lui attribuera une date d'embauche au premier jour de l'année de la date, puis fera le calcul de RTT
    // les valeurs attendues changeant selon la date passée
    @ParameterizedTest
    @ValueSource(strings = {"2019-01-01", "2021-01-01", "2022-01-01", "2032-01-01"})
    public void nombreRttTestMiTemps(String dateString ) {
        // given
        // an employe embauché au jour même du calcul
        Employe employe = new Employe();
        employe.setTempsPartiel(0.5);
        LocalDate localDate = LocalDate.parse(dateString);
        employe.setDateEmbauche(localDate);
        // when la date passée en param est la même date
        Integer nbRtt = employe.getNbRtt(localDate);
        // then
        switch (localDate.toString()) {
            case "2019-01-01":
                Assertions.assertEquals(4, nbRtt);
                break;
            case "2021-01-01":
                Assertions.assertEquals(6, nbRtt);
                break;
            case "2022-01-01":
                Assertions.assertEquals(5, nbRtt);
                break;
            case "2032-01-01":
                Assertions.assertEquals(6, nbRtt);
                break;
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"2021-01-01", "2022-01-01", "2032-01-01"})
    public void nombreRttTest2ans(String dateString ) {
        // given
        // an employe embauché au jour même du calcul
        Employe employe = new Employe();
        LocalDate localDate = LocalDate.parse(dateString);
        employe.setDateEmbauche(localDate.minusYears(2L));
        // when la date passée en param est la même date
        Integer nbRtt = employe.getNbRtt(localDate);
        // then
        switch (localDate.toString()) {
            case "2021-01-01":
                Assertions.assertEquals(9, nbRtt);
                break;
            case "2022-01-01":
                Assertions.assertEquals(8, nbRtt);
                break;
            case "2032-01-01":
                Assertions.assertEquals(10, nbRtt);
                break;
        }
    }


}
package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then nbAnneesAnciennete = 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbauchePassee(){
        //Given
        //Date d'embauche 10 ans dans le passé
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(10));
        //employe.setDateEmbauche(LocalDate.of(2012, 4, 26)); //Pas bon...

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 10
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(10);
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheFuture(){
        //Given
        //Date d'embauche 2 ans dans le futur
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "'M12345',0,1,1.0,1700.0",
            "'M12345',2,1,1.0,1900.0",
            "'M12345',0,1,0.5,850.0",
            "'T12346',0,1,1.0,1000.0",
            "'T12346',5,1,1.0,1500.0",
            "'T12346',0,2,1.0,2300.0",
            "'T12346',3,2,1.0,2600.0",
            ",0,1,1.0,1000.0",
            "'T12346',0,,1.0,1000.0"
    })
    public void testGetPrimeAnnuelleManagerPerformanceBasePleinTemps(
            String matricule,
            Integer nbAnneesAnciennete,
            Integer performance,
            Double tauxActivite,
            Double prime
    ){
        //Given
        Employe employe = new Employe("Doe", "John", matricule,
                LocalDate.now().minusYears(nbAnneesAnciennete), 2500d, performance, tauxActivite);

        //When
        Double primeObtenue = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeObtenue).isEqualTo(prime);
    }

    @Test
    public void testAugmenterSalaire(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(150.0);

        //When
        employe.augmenterSalaire(10);


        //Then
        // => 165
        Assertions.assertThat(employe.getSalaire()).isEqualTo(165);
    }

    @Test
    public void testAugmenterSalaireNegatif(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(150.0);

        //When
        employe.augmenterSalaire(-10);


        //Then
        // => 135
        Assertions.assertThat(employe.getSalaire()).isEqualTo(135);
    }

    @Test
    public void testAugmenterSalaireZero(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(150.0);

        //When
        employe.augmenterSalaire(0);


        //Then
        // => 135
        Assertions.assertThat(employe.getSalaire()).isEqualTo(150);
    }

    @ParameterizedTest
    @CsvSource({
            "2019,2,10",
            "2021,5,7",
            "2022,6,7",
            "2032,4,7"
    })
    public void testGetNbRtt(
            Integer annee,
            Integer firstDayOfYear,
            Integer nbJoursFeriesPasWeekend
    ){

        //Given
        LocalDate date = LocalDate.now().withYear(annee);
        Employe employe = new Employe();
        int nbDimancheSamedi = 104;
        switch (DayOfWeek.of(firstDayOfYear)){
            case THURSDAY:
                if(date.isLeapYear())
                    nbDimancheSamedi =  nbDimancheSamedi + 1;
                break;
            case FRIDAY:
                if(date.isLeapYear())
                    nbDimancheSamedi =  nbDimancheSamedi + 2;
                else
                    nbDimancheSamedi =  nbDimancheSamedi + 1;
            case SATURDAY:
                nbDimancheSamedi = nbDimancheSamedi + 1;
                break;
        }

        //When
        Integer nbRtt = (int) Math.ceil((date.lengthOfYear() - Entreprise.NB_JOURS_MAX_FORFAIT - nbDimancheSamedi - nbJoursFeriesPasWeekend - Entreprise.NB_CONGES_BASE) * employe.getTempsPartiel());

        //Then
        Assertions.assertThat(employe.getNbRtt(date)).isEqualTo(nbRtt);
    }

}

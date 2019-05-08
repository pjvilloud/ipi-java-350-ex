package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.fail;

public class EmployeTest {

    //Test de la méthode augmenterSalaire :
    @Rule
    public ExpectedException thrownException = ExpectedException.none();


    //Test avec un paramètre négatif, paramètre correspondant au pourcentage d'augmentation
    //La méthode doit renvoyer une exception de type EmployeException indiquant qu'on ne peut pas rétrograder le salaire
    //d'un employé, le test échoue si aucune exception n'est levée
    @Test
    public void augmenterSalaireNegativeValue()
    {
        // Given
        Employe e = new Employe();
        e.setSalaire(1800.00);

        //When
        try
        {
            e.augmenterSalaire(-0.3);
            Assertions.fail("Ce test aurait dû lancer une exception !") ;
        }
        catch(EmployeException ex)
        {
            Assertions.assertEquals("On ne peut pas rétrograder le salaire d'un employé", ex.getMessage());
        }
    }

    // On teste avec un pourcentage nul, vérification de la levée d'exception
    @Test
    public void augmenterSalaireNullValue()
    {
        //given :
        Employe e = new Employe();
        e.setSalaire(1800.00);

        //When :
        try
        {
            e.augmenterSalaire(0.0);
            Assertions.fail("Ce test aurait dû lancer une exception !") ;
        }
        catch(EmployeException ex)
        {
            Assertions.assertEquals( "Le salaire n'a pas évolué, l'augmentation étant nulle" , ex.getMessage());
        }


    }

    //On teste avec une valeur doublant le salaire, considérée irréaliste
    //On vérifie qu'une exception est levée, le salaire reste inchangé
    @Test
    public void augmenterSalaireDouble()
    {
        //Given :
        Employe e = new Employe();
        e.setSalaire(1800.00);

        //When
        try
        {
            e.augmenterSalaire(1.0);
            Assertions.fail("Ce test aurait dû lancer une exception !") ;
        }
        catch(EmployeException ex)
        {

            Assertions.assertEquals( "L'augmentation est fantaisiste, est-ce une tentative de corruption ?" , ex.getMessage());

        }


    }

    //Test témoin, l'augmentation est réaliste, et tout se passe bien
    @Test
    public void augmenterSalaireTemoin() throws Exception
    {
        //Given :
        Employe e = new Employe();
        e.setSalaire(1800.00);

        //When :
        e.augmenterSalaire(0.15);

        //Then :
        Assertions.assertEquals(2070.00, e.getSalaire().doubleValue());

    }

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

    //Test de la méthode getNbRtt
    @ParameterizedTest


    @CsvSource({
            //Calcul pour 2019 : 365 - 218 - 104 - 10 - 25 = 8
            "2019-03-31, 1, 8",
            "2019-03-31, 0.5, 4",
            "2020-03-31, 1.0, 10",
            "2020-03-31, 0.5, 5",
            "2022-03-31, 1, 10",
            "2022-03-31, 0.5, 5",
            "2027-03-31, 1, 10",
            "2027-03-31, 0.5, 5",
            "2028-03-31, 1, 9",
            "2032-06-06, 1, 11",
            "2032-02-29, 0.5, 6",

    })
    public void getNbRtt(LocalDate date, Double tempsPartiel, Integer nbRttAttendu){
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, tempsPartiel);

        //When
        Integer nbRttCalcule = employe.getNbRtt(date);

        //Then
        Assertions.assertEquals(nbRttAttendu,nbRttCalcule);

    }

}
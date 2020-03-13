package com.ipiecoles.java.java350.model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.cucumber.java8.En;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.time.LocalDate;

public class EmployeTest {

    @Mock
    private Entreprise entreprise;

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
    public void testAugmenterSalaire(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);

        //When = Exécution de la méthode à tester
        employe.augmenterSalaire(10.0);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertEquals(employe.getSalaire(), (Entreprise.SALAIRE_BASE * 1.1));
    }

    @Test
    public void testAugmenterUnSalaireNull(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(null);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThrows(NullPointerException.class, () -> {employe.augmenterSalaire(10.0);});
    }

    @Test
    public void testAugmenterSalaireNegatif(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(-10000.0);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThrows(InvalidParameterException.class, () -> {employe.augmenterSalaire(10.0);});
    }

    @Test
    public void testAugmenterSalaireParValeurNegatif(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThrows(InvalidParameterException.class, () -> {employe.augmenterSalaire(-10.0);});
    }

    @ParameterizedTest
    @CsvSource({
            "2019-01-01, 1.0, 8",
            "2020-01-01, 1.5, 15",
            "2021-01-01, 0.5, 6",
    })
    public void TestGetNbRtt(LocalDate date, Double tempsPartiel, Integer nombre){
        //Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);

        //When
        Integer nombreRtt = employe.getNbRtt(date);

        //Then
        Assertions.assertEquals(nombreRtt, nombre);

    }

}
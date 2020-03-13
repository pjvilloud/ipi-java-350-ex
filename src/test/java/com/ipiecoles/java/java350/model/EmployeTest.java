package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.EntityExistsException;
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
    @ParameterizedTest
    @CsvSource({
//  if nvxSalaire = 0.0 on attend le Entreprise.SALAIRE_BASE
            "1.0, 0.5, 760.61",
            "0.5, 1.0, 3042.44",
            "1.0, , 0.0",
            "1.0, 0.0, 0.0"
})
    public void modifierTempsPartielEmploye (Double tempsPartiel, Double nvxTempsPartiel, Double nvxSalaire) throws EmployeException {
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now().minusYears(2), Entreprise.SALAIRE_BASE,1, tempsPartiel);

        if (nvxTempsPartiel != null && nvxTempsPartiel != 0.0) {
            employe.setTempsPartiel(nvxTempsPartiel);
            Assertions.assertEquals(employe.getSalaire(), nvxSalaire);
            Assertions.assertEquals(employe.getTempsPartiel(), nvxTempsPartiel);
        }
        else {
            EmployeException e = Assertions.assertThrows(EmployeException.class, () ->  employe.setTempsPartiel(nvxTempsPartiel));
            Assertions.assertEquals("Le temps partiel ne peut pas être null ou égal à 0 !", e.getMessage());
            Assertions.assertEquals(employe.getSalaire(), Entreprise.SALAIRE_BASE);

        }
    }
}
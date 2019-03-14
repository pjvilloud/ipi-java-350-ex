package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    /**
     * this.getNombreAnneeAnciennete() not null
     * this.tempsPartiel() not null
     * Entreprise.primeAnnuelleBase() not null
     * this.performance not null
     * matricule
    
    @ParameterizedTest(name = "La prime annuelle du matricule {1} est valide")
    @CsvSource({
            "null, null, null, null, null",
            "null,'', null, null, null",
            ",'C00019', , , ",
            ",'M00001', , , ",
            ",'T00034', , , ",
            ",'', , , ",
            ",'', , , ",
            ",'', , , "
    })
    public void testGetPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tpsPartiel, Double primeAnnuelle){
        //Given
        Employe e = new Employe();
        e.setPerformance(performance);
        e.setMatricule(matricule);
        e.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));
        e.setTempsPartiel(tpsPartiel);


        //When
        Double prime = e.getPrimeAnnuelle();


        //Then
        Assertions.assertThat(prime).isEqualTo(primeAnnuelle);
    }*/

    @Test
    public void testGetNombreAnneeAncienneteNow() {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());


        //When = Exécution de la méthode à tester
        //Toujours mettre 1 seul test
        Integer nbAnnee = employe.getNombreAnneeAnciennete();


        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnee).isEqualTo(0);

    }

    @Test
    public void testGetNombreAnneeAncienneteNull() {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);


        //When = Exécution de la méthode à tester
        Integer nbAnnee = employe.getNombreAnneeAnciennete();


        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnee).isEqualTo(0);

    }

    @Test
    public void testGetNombreAnneeAncienneteNmoins2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));


        //When
        Integer nbAnnee = employe.getNombreAnneeAnciennete();


        //Then
        Assertions.assertThat(nbAnnee).isGreaterThanOrEqualTo(2);

    }

    @Test
    public void testGetNombreAnneeAncienneteNplus2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));


        //When
        Integer nbAnnee = employe.getNombreAnneeAnciennete();


        //Then
        Assertions.assertThat(nbAnnee).isGreaterThanOrEqualTo(0);

    }
}
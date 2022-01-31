package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    //cas1 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2023 | nbAnneeAnciennete : 0

    /*@Test
    public void testGetNbAnneeAncienneteWithDateEmbaucheFuture(){
        //Given
        LocalDate dateEmbauche = LocalDate.of(2023, 1, 5);
        Employe employe = new Employe("Doe","John","T12345",dateEmbauche,Entreprise.SALAIRE_BASE, 4,1d);


        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

    //cas 2 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2022 | nbAnneeAnciennete : 0
    public void testGetNbAnneeAncienneteWithDateEmbaucheEgale(){


    }

    //cas 3 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2021 | nbAnneeAnciennete : 1
    public void testGetNbAnneeAncienneteWithDateEmbaucheFut(){

    }


    //cas 4 : DateNow = now | dateEmbauche = null | nbAnneeAnciennete : 0
    public void testGetNbAnneeAncienneteWithDateEmbaucheFutur(){

    }
*/
   @Test
   public void test_augmenterSalaire_Ok() throws EmployeException {
       //Given
       Employe employe = new Employe();
       employe.setSalaire(2000d);

       //When
       employe.augmenterSalaire(10);

       //Then
       Assertions.assertEquals(2200d,employe.getSalaire());
   }

    @Test
    public void test_augmenterSalaire_WhenPourcentageNegative() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            employe.augmenterSalaire(-5d);
        });

        //Then
        Assertions.assertEquals("Le pourcentage d'augmentation ne peut être égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void test_augmenterSalaire_WhenPourcentage0() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            employe.augmenterSalaire(0);
        });

        //Then
        Assertions.assertEquals("Le pourcentage d'augmentation ne peut être égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void test_augmenterSalaire_WhenSalaireNegative() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(-2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            employe.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void test_augmenterSalaire_WhenSalaire0() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(0d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            employe.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void test_augmenterSalaire_WhenSalaireNull() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(null);
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            employe.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0",thrown.getMessage());
    }

    @ParameterizedTest(name = "Matricule {0}, performance {1}, anciennete {2}, temps partiel {3} => prime {4}")
    @CsvSource({
            "'2019-01-01',9",
            "'2022-01-01',11",
            "'2032-01-01',10",
    })
    public void testgetNbRtt(LocalDate d , Integer nbrRttAttendu){
        //Given
        Employe employe = new Employe();
        //When
        int nbrRttObtenu = employe.getNbRtt(d);
        //Then
        Assertions.assertEquals(nbrRttAttendu,nbrRttObtenu);
    }
}

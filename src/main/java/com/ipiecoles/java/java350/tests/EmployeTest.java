package com.ipiecoles.java.java350.tests;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheEqualNull(){

        //Given
        Employe e1 = new Employe();
        e1.setDateEmbauche(null);
        Integer nbAnneeAncien = e1.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0,nbAnneeAncien);
    }

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheIsFutur(){

        //Given
        Employe e1 = new Employe();
        e1.setDateEmbauche(LocalDate.now().plusYears(1));
        Integer nbAnneeAncien = e1.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0,nbAnneeAncien);
    }

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheIsToday(){

        //Given
        Employe e1 = new Employe();
        e1.setDateEmbauche(LocalDate.now());
        Integer nbAnneeAncien = e1.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0,nbAnneeAncien);
    }

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheIsPast(){
        //Given
        Employe e1 = new Employe();
        e1.setDateEmbauche(LocalDate.now().minusYears(1));
        Integer nbAnneeAncien = e1.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(1,nbAnneeAncien);
    }

    @ParameterizedTest(name = "Matricule  {0}, perf  {1}, anciennete {2}, temps partiel {3} => prime {4}")
    @CsvSource({
            "'T12345',1,0,1.0,1000.0",
            "'T12345',1,0,0.5,500.0",
            "'T12345',1,2,1.0,1200.0",
            ",1,0,1.0,1000.0",
            "'T12345',,0,1.0,1000.0",
            "'M12345',1,0,1.0,1700.0",
            "'M12345',1,3,1.0,2000.0",
            "'T12345',2,0,1.0,2300.0",
            "'T12345',2,1,1.0,2400.0",
    })
    public void testGetPrimeAnnuelle(String pMatricule, Integer pPerformance,
                                     Long pNbAnneeAncien, Double pTempsPartiel, Double pPrimeAttendue){

        //Given
        Employe e1 = new Employe();
        e1.setMatricule(pMatricule);
        e1.setPerformance(pPerformance);
        e1.setDateEmbauche(LocalDate.now().minusYears(pNbAnneeAncien));
        e1.setTempsPartiel(pTempsPartiel);

        //When
        Double prime = e1.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(pPrimeAttendue,prime);
    }

    @Test
    public void testAugmenterSalaire() throws EmployeException {

        //Given
        Employe e1 = new Employe();
        e1.setSalaire(2000d);

        //When
        e1.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(2200d,e1.getSalaire());
    }

    @Test
    public void testAugmenterSalaireEqualZero() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setSalaire(0d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            e1.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void testAugmenterSalairePourcentageEqualZero() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setSalaire(2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            e1.augmenterSalaire(0);
        });

        //Then
        Assertions.assertEquals("Le pourcentage d'augmentation ne peut être égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void testAugmenterSalaireEqualNull() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setSalaire(null);
        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            e1.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0  ",thrown.getMessage());
    }

    @Test
    public void testAugmenterSalaireIsNegative() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setSalaire(-2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            e1.augmenterSalaire(50);
        });

        //Then
        Assertions.assertEquals("Le salaire de cet employé n'est pas défini ou est égal ou inférieur à 0",thrown.getMessage());
    }

    @Test
    public void testAugmenterSalairePourcentageIsNegative() throws EmployeException {
        //Given
        Employe e1 = new Employe();
        e1.setSalaire(2000d);

        //When
        EmployeException thrown = Assertions.assertThrows(EmployeException.class, () ->{
            e1.augmenterSalaire(-5d);
        });

        //Then
        Assertions.assertEquals("Le pourcentage d'augmentation ne peut être égal ou inférieur à 0",thrown.getMessage());
    }

    @ParameterizedTest(name = "Matricule {0}, performance {1}, anciennete {2}, temps partiel {3} => prime {4}")
    @CsvSource({
            "'2019-01-01',9",
            "'2022-01-01',11",
            "'2032-01-01',10",
    })
    public void testGetNbRtt(LocalDate pDate , Integer pNbRttAttendu){
        //Given
        Employe e1 = new Employe();
        //When
        int nbrRttObtenu = e1.getNbRtt(pDate);
        //Then
        Assertions.assertEquals(pNbRttAttendu,nbrRttObtenu);
    }
}
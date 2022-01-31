package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheNull_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(null);
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheFutur_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now().plusYears(1));
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheToday_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now());
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbauchePast_success(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now().minusYears(1));
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(1,nbAnnesAnciennete);
    }

    @ParameterizedTest(name = "Matricule {0}, performance {1}, anciennete {2}, temps partiel {3} => prime {4}")
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
    public void testGetPrimeAnnuelle(String matricule, Integer performance,
                                     Long nbAnneesAnciennete, Double tempsPartiel,
                                     Double primeAttendue){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
        employe.setTempsPartiel(tempsPartiel);
        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertEquals(primeAttendue,prime);
    }

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
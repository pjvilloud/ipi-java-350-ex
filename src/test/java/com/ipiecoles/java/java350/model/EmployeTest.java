package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class EmployeTest {

    @Autowired
    private EmployeRepository employeRepository;

    //For method getNombreAnneeAnciennete()
    @Test
    public void testNbAnneeAncienneteNow(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNowMinus2(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    public void testNbAnneeAncienneteNowPlus3(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNull(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    //For method getPrimeAnnuelle()
    @ParameterizedTest
    @CsvSource({
            "'C12345', 1.0, 0, 1, 1000.0",
            //"'C12345', 0.5, 0, 1, 500.0",
            "'M12345', 1.0, 0, 1, 1700.0",
            "'C12345', 1.0, 0, 2, 2300.0",

    })
    public void testGetPrimeAnnuelle(String matricule, Double tempsPartiel, Integer nbAnneeAnciennete, Integer perfomance, Double primeCalculee){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setPerformance(perfomance);

        //When = Exécution de la méthode à tester
        Double prime = employe.getPrimeAnnuelle();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(prime).isEqualTo(primeCalculee);
    }

    @Test
    public void testAugmenterSalaire() throws EmployeException {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        Double augmentation = 10.0;

        //When = Exécution de la méthode à tester
        employe.augmenterSalaire(augmentation);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1210.0);
    }
    @Test
    public void testAugmenterSalairePourcentage0() throws EmployeException {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        Double augmentation = 0.0;

        try{
            employe.augmenterSalaire(augmentation);
            Assertions.fail("Aurait du lancer une exception");
        } catch(Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            //Vérifie le contenue du message
            Assertions.assertThat(e.getMessage()).isEqualTo("Attention le pourcentage est égale à 0 !");
        }

    }
    @Test
    public void testAugmenterSalairePourcentageNegatif() throws EmployeException {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        Double augmentation = -4.0;

        try{
            employe.augmenterSalaire(augmentation);
            Assertions.fail("Aurait du lancer une exception");
        } catch(Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            //Vérifie le contenue du message
            Assertions.assertThat(e.getMessage()).isEqualTo("Attention le pourcentage est négatif !");
        }

    }
    @Test
    public void testAugmenterSalaireNull() throws EmployeException {
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setSalaire(null);
        Double augmentation = 4.0;

        try{
            employe.augmenterSalaire(augmentation);
            Assertions.fail("Aurait du lancer une exception");
        } catch(Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            //Vérifie le contenue du message
            Assertions.assertThat(e.getMessage()).isEqualTo("Attention salaire null !");
        }

    }

}

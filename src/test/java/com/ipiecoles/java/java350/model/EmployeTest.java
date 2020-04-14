package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    public void augmenterSalaireNormalTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(2200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireFloatTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(10.5);

        //Then
        Assertions.assertEquals(2210d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireSupPourcentageTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(110);

        //Then
        Assertions.assertEquals(4200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNegativePourcentageTest() throws EmployeException {
        //Given
        Employe employe = new Employe("Test", "Roger", "C00002", LocalDate.now(), null, 3, 7.0 );
        employe.setSalaire(2000d);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
                    //When
                    employe.augmenterSalaire(-10);
                }
        )//Then
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le pourcentage ne peut pas être négatif");
    }

    @Test
    public void augmenterSalaireNullPourcentageTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(0);

        //Then
        Assertions.assertEquals(2000d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireWithoutSalaireTest() throws EmployeException {
        //Given
        Employe e = new Employe();

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(1673.1000000000001, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNullSalaireTest() throws EmployeException{
        //Given
        Employe employe = new Employe("Test", "Roger", "C00002", LocalDate.now(), null, 3, 7.0 );
        Double pourcentage = 10D;

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
                    //When
                    employe.augmenterSalaire(pourcentage);
                }
        )//Then
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le salaire est null");
    }

    @ParameterizedTest
    @CsvSource({
            "'M00012',, 2500, 3, 7.0,11900.0",
            "'M00012',2020-04-08 ,0, 3, 7.0, 11900.0",
            "'M00012',2020-04-08 , 2500, 3, 7.0, 11900.0",
            "'C00012',2020-04-08 ,0, 3, 7.0, 23100.0",
            "'C00012',2020-04-08 ,0,1, 7.0, 7000.0",
            ",2020-04-08 ,0, 3, 7.0, 23100.0",
            "'M00012',2020-04-08 , 2500, 0, 7.0, 11900.0",
            "'M00012',2020-04-08 , 2500, 1,0,0.0",
            "'M00012',2025-04-08 , 2500, 1,0,0.0",
            "'M00012',2015-04-08 , 2500, 1,0,0.0"
    })
    public void getPrimeAnnuelleTest(String matricule, LocalDate dateEmbauche, double salaire, int performance, double tempsPartiel, Double result){
        //Given
        Employe employe = new Employe("Test", "Roger", matricule, dateEmbauche, salaire, performance, tempsPartiel );

        //When
        Double resultFunction = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(resultFunction, result);
    }

    @Test
    public void getPrimeAnnuelleNULLTest(){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500D, null, 7.0 );

        //When
        Double resultFunction = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(11900.0,resultFunction);
    }

    @ParameterizedTest
    @CsvSource({
            "'M00012',2020-04-08 , 2500, 3,0,25",
            "'M00012',2025-04-08 , 2500, 3,0,25",
            "'M00012',2015-04-08 , 2500, 3,0,30"
    })
    public void getNbCongesTest(String matricule, LocalDate dateEmbauche, double salaire, int performance, double tempsPartiel, Integer result){
        //Given
        Employe employe = new Employe("Test", "Roger", matricule, dateEmbauche, salaire, performance, tempsPartiel );

        //When
        Integer resultFunction = employe.getNbConges();

        //Then
        Assertions.assertEquals(resultFunction, result);
    }

    @ParameterizedTest
    @CsvSource({
            "2020-04-10 ,0.0, 0",
            "2020-04-11 ,7.0, 63",
            "2020-04-12 ,7.0, 63",
            "2020-04-13 ,7.0, 63",
            "2020-04-14 ,7.0, 63",
            "2020-04-15 ,7.0, 63",
            "2020-04-16 ,7.0, 63",
            "2020-04-17 ,7.0, 63",
            "2019-04-10 ,7.0, 63",
            "2019-04-11 ,7.0, 63",
            "2019-04-12 ,7.0, 63",
            "2019-04-13 ,7.0, 63",
            "2019-04-14 ,7.0, 63",
            "2019-04-16 ,7.0, 63",
            "2032-04-17 ,7.0, 70",
    })
    public void getNbRttTest(LocalDate d, double tempsPartiel ,Integer result){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, tempsPartiel );

        //When
        Integer resultFunction = employe.getNbRtt(d);

        //Then
        Assertions.assertEquals(resultFunction, result);
    }

    @Test
    public void getNbRttTest2(){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 0.0 );

        //When
        Integer resultFunction = employe.getNbRtt();

        //Then
        Assertions.assertEquals( 0,resultFunction);
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "0",
    })
    public void setIdTest(Long id){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        employe.setId(id);

        //Then
        Assertions.assertEquals(employe.getId(), id);
    }

    @ParameterizedTest
    @CsvSource({
            "'Roger'",
            "'DuPont'",
            "''"
    })
    public void setNomTest(String nom){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        employe.setNom(nom);

        //Then
        Assertions.assertEquals(employe.getNom(), nom);
    }

    @ParameterizedTest
    @CsvSource({
            "'Roger'",
            "'DuPont'",
            "''"
    })
    public void setPrenomTest(String prenom){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        employe.setPrenom(prenom);

        //Then
        Assertions.assertEquals(employe.getPrenom(), prenom);
    }

    @ParameterizedTest
    @CsvSource({
            "'M00012'",
            "'C00011'",
            "''"
    })
    public void setMatriculeTest(String matricule){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        employe.setMatricule(matricule);

        //Then
        Assertions.assertEquals(employe.getMatricule(), matricule);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0",
            "7.0"
    })
    public void setTempsPartielTest(Double tempsPartiel){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        employe.setTempsPartiel(tempsPartiel);

        //Then
        Assertions.assertEquals(employe.getTempsPartiel(), tempsPartiel);
    }

    @Test
    public void hashCodeTest(){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        int result = employe.hashCode();

        //Then
        Assertions.assertEquals( 7.1814865E8,result);
    }

    @ParameterizedTest
    @CsvSource({
            "'test', false",
            "new Employe(\"Test\", \"Roger\", \"M00012\", LocalDate.now(), 2500.0, 2, 7.0 ), 'true'"
    })
    public void equalsTest(Object obj, boolean resultat){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, 7.0 );

        //When
        boolean result = employe.equals(obj);

        //Then
        Assertions.assertEquals(result, resultat);
    }
}
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
            "'M00012',2020-04-08 , 2500, 0, 7.0, 11900.0",
            "'M00012',2020-04-08 , 2500, 3,0,0.0",
    })
    public void getPrimeAnnuelleTest(String matricule, LocalDate dateEmbauche, double salaire, int performance, double tempsPartiel, Double result){
        //Given
        Employe employe = new Employe("Test", "Roger", matricule, dateEmbauche, salaire, performance, tempsPartiel );

        //When
        Double resultFunction = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(resultFunction, result);
    }

    @ParameterizedTest
    @CsvSource({
            "2020-04-08 ,0.0, 0",
            "2020-04-08 ,7.0, 63",
    })
    public void getNbRttTest(LocalDate d, double tempsPartiel ,Integer result){
        //Given
        Employe employe = new Employe("Test", "Roger", "M00012", LocalDate.now(), 2500.0, 2, tempsPartiel );

        //When
        Integer resultFunction = employe.getNbRtt(d);

        //Then
        Assertions.assertEquals(resultFunction, result);
    }
}
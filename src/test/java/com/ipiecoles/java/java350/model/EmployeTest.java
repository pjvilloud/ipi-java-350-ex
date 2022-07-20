
package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeTest {
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then nbAnneesAnciennete = 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbauchePassee(){
        //Given
        //Date d'embauche 10 ans dans le passé
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(10));
        //employe.setDateEmbauche(LocalDate.of(2012, 4, 26)); //Pas bon...

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 10
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(10);
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheFuture(){
        //Given
        //Date d'embauche 2 ans dans le futur
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        // => 0
        Assertions.assertThat(nbAnneesAnciennete).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "'M12345',0,1,1.0,1700.0",
            "'M12345',2,1,1.0,1900.0",
            "'M12345',0,1,0.5,850.0",
            "'T12346',0,1,1.0,1000.0",
            "'T12346',5,1,1.0,1500.0",
            "'T12346',0,2,1.0,2300.0",
            "'T12346',3,2,1.0,2600.0",
            ",0,1,1.0,1000.0",
            "'T12346',0,,1.0,1000.0"
    })
    public void testGetPrimeAnnuelleManagerPerformanceBasePleinTemps(
            String matricule,
            Integer nbAnneesAnciennete,
            Integer performance,
            Double tauxActivite,
            Double prime
    ){
        //Given
        Employe employe = new Employe("Doe", "John", matricule,
                LocalDate.now().minusYears(nbAnneesAnciennete), 2500d, performance, tauxActivite);

        //When
        Double primeObtenue = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeObtenue).isEqualTo(prime);
    }

    @Test
    void testAugmenterSalaire() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(1500d);

        //when
        employe.augmenterSalaire(2);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1530.0);
    }

    @Test
    void testAugmenterSalaireZeroPercentage() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(1500d);

        //when
        Throwable t = Assertions.catchThrowable(() -> {employe.augmenterSalaire(0);});

        //Then
        Assertions.assertThat(t).isInstanceOf(EmployeException.class).hasMessage("the percentage you put is incorrect it should be more than zero");
    }

    @Test
    void testAugmenterSalaireIsZero() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(0d);

        //when
        employe.augmenterSalaire(2);
        Double employeSalary = employe.getSalaire();

        //Then
        Assertions.assertThat(employeSalary).isZero();
    }

    @ParameterizedTest
    @CsvSource( {
            "10, 2200",
            "20, 2400",
            "30, 2600",
            "40, 2800",
            "50, 3000"
    })
    void testAugmenterSalaireWithoutError(double pourcentage, double expectedSalary) throws EmployeException {
        //Given
        Employe employe = new Employe("Doe", "John", "C123456", LocalDate.now(), 2000d, 1, 1.0);

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(expectedSalary);
    }

    @ParameterizedTest
    @CsvSource({
            "-1,9",
            "0,10",
            "0,10",
            "3,8",
    })
    void testgetNbRtt(Integer date, Integer nbrRttAttendu){
        //Given
        LocalDate d = LocalDate.now().minusYears(date);
        Employe employe = new Employe();
        //When
        int watingNbr = employe.getNbRtt(d);
        //Then
        Assertions.assertThat(watingNbr).isEqualTo(nbrRttAttendu);
    }

    @Test
    void testgetNbbRttHalfTime() {
        // Given
        Employe employe = new Employe();
        Employe mockEmploye = Mockito.spy(employe);
        mockEmploye.setTempsPartiel(0.5d);

        // When
        Integer nbRtt = mockEmploye.getNbRtt(LocalDate.of(2019, 1, 1));

        // Then
        Assertions.assertThat(nbRtt).isEqualTo(4);
    }

    @Test
    void testGetNbConges() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(5));

        //When
        Integer conges = employe.getNbConges();

        //Then
        Assertions.assertThat(conges).isEqualTo(30);
    }

    @Test
    void testGetterGetRtt() {
        //Given
        Employe employe = new Employe();

        //When
        Integer actualRtt = employe.getNbRtt();
        Integer expectedRtt = employe.getNbRtt(LocalDate.now());

        //Then
        Assertions.assertThat(actualRtt).isEqualTo(expectedRtt);
    }

    @Test
    void testGettersAndSetters() {
        //Given
        Employe employe = new Employe();

        //When
        employe.setId(1000L);
        employe.setPrenom("John");

        //Then
        Assertions.assertThat(employe.getId()).isEqualTo(1000L);
        Assertions.assertThat(employe.setNom("Doe")).isEqualTo(employe);
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
    }

    @Test
    void testObjectClassMethods() {
        //Given
        Employe employe1 = new Employe();
        Employe employe2 = new Employe();

        //When
        int hashCode = employe1.hashCode();

        //Then
        Assertions.assertThat(hashCode).isEqualTo(1205956486);
        Assertions.assertThat(employe1).isEqualTo(employe2);
        employe1.setId(22000l);
        Assertions.assertThat(employe1)
                .isNotEqualTo(employe2)
                .hasToString("Employe{id=22000, nom='null', prenom='null', matricule='null', dateEmbauche=null, salaire=1521.22, performance=1, tempsPartiel=1.0}");
    }

}
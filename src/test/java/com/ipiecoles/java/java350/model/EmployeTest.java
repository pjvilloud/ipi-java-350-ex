package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    public void testGetAnneeAcienneteDateEmbaucheNull(){
        //GIVEN
        Employe employe = new Employe();
        employe.setDateEmbauche(null);
        // WHEN
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();
        //THEN
        Assertions.assertThat(anneeAnciennete).isNull();
    }

    @Test
    public void testGetAnneeAcienneteDateEmbaucheInfNow() {
        //GIVEN
        Employe employe = new Employe("Doe", "Jonh", "T12345", LocalDate.now().minusYears(6), 1500d, 1, 1.0);
        // WHEN
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();
        //THEN
        Assertions.assertThat(anneeAnciennete).isEqualTo(6);
    }

    @Test
    public void testGetAnneeAcienneteDateEmbaucheSupNow(){
        //GIVEN
        Employe employe = new Employe("Doe","Jonh","T12345", LocalDate.now().plusYears(6),1500d,1,1.0);
        // WHEN
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();
        //THEN
        Assertions.assertThat(anneeAnciennete).isNull();
    }

    @ParameterizedTest(name = "Perf{0}, matricule {1} txActivite{2}, anciennete {3} => prime {4} ")
    @CsvSource({"1,'T12345',1.0,0,1000.0",
            "1,'T12345',0.5,0,500.0",
            "2,'T12345',1.0,0,2300.0",
            "1,'T12345',1.0,2,1200.0",
            "2,'T12345',1.0,1,2400.0",
            "1,'M12345',1.0,0,1700.0",
            "1,'M12345',1.0,3,2000.0",
            "1,null,1.0,0,1000.0"})
    public void testGetPrimeAnnuelle(Integer perforance,String matricule,Double tauxActivite,Long nbAnneesAnciennete,Double primeAttendue){

        //GIVEN
        Employe employe = new Employe("Doe","Jonh",matricule, LocalDate.now().minusYears(nbAnneesAnciennete),1500d,perforance,tauxActivite);
        // WHEN
        Double prime = employe.getPrimeAnnuelle();
        //THEN
        Assertions.assertThat(prime).isEqualTo(primeAttendue);
    }



    @Test
    public void testAugmenterSalaireEmploye(){

        //GIVEN
        Double salaireBase = 1600.0;
        Employe employe = new Employe("Doe", "John", "T40404", LocalDate.now(), salaireBase, 1, 1.0);
        Double pourcentage= 10d;
        //WHEN
        Double nouveauSalaire = employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(nouveauSalaire).isEqualTo(1760d);
    }


    @Test
    public void testAugmenterSalaireNull(){

        //GIVEN
        Double salaire = null;
        Employe employe = new Employe("Doe" ,"John", null, LocalDate.now(), salaire, 1, 1.0);
        Double pourcentage = 10d;

        //WHEN
        Double nouveauSalaire = employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(nouveauSalaire).isEqualTo(1673.342);

    }

    @Test
    public void testAugmenterSalairePourcentageNegatif() {

        //GIVEN
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), 1700d, 1, 1.0);
        Double pourcentage = -1.0;

        //WHEN
        Double nouveauSalaire = employe.augmenterSalaire(pourcentage);

        //THEN
        assertNull(nouveauSalaire);
    }

    @Test
    public void testAugmenterSalairePourcentage0() {

        //GIVEN
        Double salaire = 1700d;
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), salaire, 1, 1.0);
        Double pourcentage = 0.0;

        //WHEN
        Double nouveauSalaire = employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(nouveauSalaire).isEqualTo(salaire);
    }

    @ParameterizedTest(name = "Perf {0}, matricule {1}, txActivite {2}, anciennete {3}, salaire {4}, pourcentage {5} => salaireAttendu {6}")
    @CsvSource({
            "1, 'T12345', 1.0, 10, 1600.00, 10.0, 1760.00",
            "2, 'T12345', 1.0, 0, 1960.00, 0.0, 1960.00",
            "1, 'T12345', 0.5, 4, 2000.00, 25.0, 2500.00"
    })
    void testAugmenterSalairePlusieursValeurs(Integer performance, String matricule, Double tauxActivite, Long nbAnneesAnciennete, Double salaire, Double pourcentage,
                                              Double salaireAttendu){
        //GIVEN
        Employe employe = new Employe("Doe", "John", matricule,
                LocalDate.now().minusYears(nbAnneesAnciennete), salaire,
                performance, tauxActivite);
        //WHEN
        Double salaireAugmente = employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(salaireAugmente).isEqualTo(salaireAttendu);
    }

}
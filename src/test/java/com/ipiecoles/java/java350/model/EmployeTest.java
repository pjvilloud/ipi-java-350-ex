package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class EmployeTest {

    @Test
    void getNombreAnneeAnciennete() {
        //given
        Employe employe= new Employe();
        employe.setDateEmbauche(LocalDate.now());
        //when
        Integer nombreAnneeAnciennete = employe.getNombreAnneeAnciennete();
        //then
        assertThat(nombreAnneeAnciennete).isEqualTo(0);
    }
    @Test
    void getNombreAnneeAncienneteSiAnneeEmbaucheSuperieurAlaDateDuJour() {
        //given
        Employe employe= new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));
        //when
        Integer nombreAnneeAnciennete = employe.getNombreAnneeAnciennete();
        //then
        assertThat(nombreAnneeAnciennete).isEqualTo(0);
    }

    @Test
    void nombreAnneeAncienneteDansLePasse(){
        //given
        Employe employe= new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));
        //when
        Integer nombreAnneeAnciennete = employe.getNombreAnneeAnciennete();
        //then
        assertThat(nombreAnneeAnciennete).isEqualTo(2);
    }

    @Test
    void nombreAnneeAncienneteEgalNul(){
        //given
        Employe employe= new Employe();
        employe.setDateEmbauche(null);
        //when
        Integer nombreAnneeAnciennete = employe.getNombreAnneeAnciennete();
        //then
        assertThat(nombreAnneeAnciennete).isNull();
    }
    @ParameterizedTest
    @CsvSource({
            "'Mh255',0,1,1.0,1700"
    })
    void getPrimeAnnuelle(String matricule, Integer nbAnneeAnciennete,Integer performance,Double tempsPartiel,Double primeCalcule){
        //given
        Employe employe= new Employe("test","test",matricule,LocalDate.now().minusYears(nbAnneeAnciennete),2000.0,performance,tempsPartiel);
        //when
        Double primeAnnuelle = employe.getPrimeAnnuelle();
        //then
        assertThat(primeAnnuelle).isEqualTo(primeCalcule);
    }
}
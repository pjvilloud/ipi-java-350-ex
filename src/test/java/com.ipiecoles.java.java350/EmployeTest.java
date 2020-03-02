package com.ipiecoles.java.java350;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testNombreAnneeAnciennete(){
        Employe employeDeTest = new Employe();
        employeDeTest.setDateEmbauche(LocalDate.of(2020, 03, 01));


        //When = Exécution de la méthode à tester

        Boolean nbOk = employeDeTest.getNombreAnneeAnciennete().equals(1);

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbOk).isFalse();
    }


}

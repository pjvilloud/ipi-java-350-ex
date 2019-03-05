package model;

import com.ipiecoles.java.java350.model.Employe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

class EmployeTest {

    @Test
    void getNombreAnneAncienneteWhenDateEmbaucheIsNull() {
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        Assertions.assertEquals(0, (int) employe.getNombreAnneeAnciennete(), "nombreAnneeAnciennete should be zero for employe without dateEmbauche.");
    }

    @Test
    void getNombreAnneeAncienneteWhenDateEmbaucheIsToday() {

        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertEquals(0, (int) anneeAnciennete);
    }

    @Test
    void getNombreAnneeAncienneteWhenDateEmbaucheWasYesterday() {

        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusDays(1));

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertEquals(0, (int) anneeAnciennete);
    }

    @Test
    void getNombreAnneeAncienneteWhenDateEmbaucheWasTwoYearsFromNow() {

        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertEquals(2, (int) anneeAnciennete);
    }

    @Test
    void getNombreAnneeAncienneteWhenDateEmbaucheIsFuture() {

        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertEquals(0, (int) anneeAnciennete);
    }


}

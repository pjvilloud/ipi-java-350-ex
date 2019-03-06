package model;

import com.ipiecoles.java.java350.model.Employe;

import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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


    @ParameterizedTest(name = "Prime Anuelle for {1} should be {4}")
    @CsvSource({
            "1, 'M021113', 2, 1.0, 1900.0",
            "2, 'C023334', 1, 0.8, 1920.0",
            "1, 'T02112', 1, 1.0, 1100.0"
    })
    void getPrimeAnuelleManager(Integer performance, String matricule, int anneeAnciennete, Double tempsPartiel, Double expected) {
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(anneeAnciennete));
        employe.setPerformance(performance);
        employe.setTempsPartiel(tempsPartiel);

        Double primeAnnuelle = employe.getPrimeAnnuelle();

        Assertions.assertEquals(expected,
                primeAnnuelle
        );
    }

}

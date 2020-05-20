package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

public class EmployeMaker {

    private static EmployeBuilder technicienAPleinTemps() {
        return EmployeBuilder.anEmploye()
                .withMatricule("T12345")
                .withNom("Doe")
                .withPrenom("John")
                .withDateEmbauche(LocalDate.now())
                .withSalaire(Entreprise.SALAIRE_BASE)
                .withPerformance(Entreprise.PERFORMANCE_BASE)
                .withTempsPartiel(1.0);
    }
}

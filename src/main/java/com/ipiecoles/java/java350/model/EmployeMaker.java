package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

public class EmployeMaker {

    public static EmployeBuilder technicienAPleinTemps(){
        return EmployeBuilder.anEmploye()
                .withMatricule("T00001")
                .withNom("Doe")
                .withPrenom("John")
                .withDateEmbauche(LocalDate.now())
                .withSalaire(Entreprise.SALAIRE_BASE)
                .withPerformance(Entreprise.PERFORMANCE_BASE)
                .withTempsPartiel(1.0);
    }
}

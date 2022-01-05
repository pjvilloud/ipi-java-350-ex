package com.ipiecoles.java.java350.model;

import java.time.LocalDate;
public class EmployeMaker {

    public static EmployeBuilder employeTechnicienPleinTemps(){
        return EmployeBuilder.anEmploye()
                .withNom("Doe")
                .withPrenom("John")
                .withMatricule("T12345")
                .withSalaire(Entreprise.SALAIRE_BASE)
                .withTempsPartiel(1d)
                .withDateEmbauche(LocalDate.now())
                .withPerformance(4);
    }

}
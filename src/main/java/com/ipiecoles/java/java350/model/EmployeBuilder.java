package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

public final class EmployeBuilder {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private LocalDate dateEmbauche;
    private Double salaire = Entreprise.SALAIRE_BASE;
    private Integer performance = Entreprise.PERFORMANCE_BASE;
    private Double tempsPartiel = 1.0;

    private EmployeBuilder() {
    }

    public static EmployeBuilder anEmploye() {
        return new EmployeBuilder();
    }

    public EmployeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public EmployeBuilder withNom(String nom) {
        this.nom = nom;
        return this;
    }

    public EmployeBuilder withPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public EmployeBuilder withMatricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public EmployeBuilder withDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
        return this;
    }

    public EmployeBuilder withSalaire(Double salaire) {
        this.salaire = salaire;
        return this;
    }

    public EmployeBuilder withPerformance(Integer performance) {
        this.performance = performance;
        return this;
    }

    public EmployeBuilder withTempsPartiel(Double tempsPartiel) {
        this.tempsPartiel = tempsPartiel;
        return this;
    }

    public Employe build() {
        Employe employe = new Employe();
        employe.setId(id);
        employe.setNom(nom);
        employe.setPrenom(prenom);
        employe.setMatricule(matricule);
        employe.setDateEmbauche(dateEmbauche);
        employe.setSalaire(salaire);
        employe.setPerformance(performance);
        employe.setTempsPartiel(tempsPartiel);
        return employe;
    }
}

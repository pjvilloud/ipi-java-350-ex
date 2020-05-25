package com.ipiecoles.java.java350.acceptance;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeAcceptanceTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    /*@Step("J'embauche une personne appelée <prenom> <nom> diplômée d'un <diplome> en tant que <poste> à <txActivite>.")
    public void embauche(String prenom, String nom, String diplome, String poste, String txActivite) throws EmployeException {
        NiveauEtude niveauEtude = NiveauEtude.valueOf(diplome.toUpperCase());
        Poste postEmp = Poste.valueOf(poste.toUpperCase());
        Double tempAct = 1.0;
        if(txActivite == "mi-temps"){
            tempAct = 0.5;
        }
        employeService.embaucheEmploye(nom, prenom, postEmp, niveauEtude, tempAct);
    }

    @Step("Soit un employé appelé <prenom> <nom> de matricule <matricule>")
    public void createEmploye(String prenom, String nom, String matricule){
        Employe e = new Employe();
        e.setPrenom(prenom);
        e.setNom(nom);
        e.setMatricule(matricule);
        employeRepository.save(e);
    }

    @Step("J'obtiens bien un nouvel employé appelé <prenom> <nom> portant le matricule <matricule> et touchant un salaire de <salaire>€.")
    public void verifNewEmploye(String prenom, String nom, String matricule, String salaire){
        System.out.println(prenom);
        System.out.println(nom);
        System.out.println(matricule);
        System.out.println(salaire);
    }

    @Step("Je calcule les performances d'un commercial de matricule <matricule> ayant pour objectif <objectifCa>€ et ayant réalisé un chiffre d'affaire traité de <caTraite>€")
    public void calculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        Employe e = new Employe();
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(salaire, e.getSalaire());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(nom, e.getNom());
    }

    public Employe(String nom, String prenom, String matricule, LocalDate dateEmbauche, Double salaire, Integer performance, Double tempsPartiel) {
    }*/
}

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StepDefinitions {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Step("Soit un employé appelé <prenom> <nom> de matricule <matricule>")
    public void insertEmploye(String prenom, String nom, String matricule) throws EmployeException {
        employeRepository.save(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));
    }

    @Step("On vire tous les employés s'il y en a")
    public void purgeBdd() throws EmployeException {
        employeRepository.deleteAll();
    }

    @Step("J'embauche une personne appelée <prenom> <nom> diplômée d'un <diplome> en tant que <poste> avec un taux d'activité de <txActivite>")
    public void embaucheEmploye(String prenom, String nom, String diplome, String poste, Double txActivite) throws EmployeException {
        employeService.embaucheEmploye(nom, prenom, Poste.valueOf(poste.toUpperCase()), NiveauEtude.valueOf(diplome.toUpperCase()), txActivite);
    }

    @Step("J'obtiens bien un nouvel employé appelé <prenom> <nom> portant le matricule <matricule> et touchant un salaire de <salaire> €")
    public void checkEmploye(String prenom, String nom, String matricule, Double salaire) {
        Employe e = employeRepository.findByMatricule(matricule);
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(salaire, e.getSalaire());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(nom, e.getNom());
    }

    @Step("Soit un commercial appelé <prenom> <nom> de matricule <matricule> et de performance <performance>")
    public void insertCommercial(String prenom, String nom, String matricule, Integer performance) throws EmployeException {
        employeRepository.save(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, 1.0));
    }

    @Step("Je calcule la nouvelle performance du commercial <matricule> ayant réalisé un chiffre d'affaire de <caTraite> €, pour un chiffre d'affaire cible de <objectifCa> €")
    public void embaucheEmploye(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
    }

    @Step("J'obtiens bien une nouvelle performance de <perfObtenue> pour le commercial <matricule>")
    public void checkPerformance(Integer perfObtenue, String matricule) {
        Employe e = employeRepository.findByMatricule(matricule);
        Assertions.assertEquals(perfObtenue, e.getPerformance());
    }
}
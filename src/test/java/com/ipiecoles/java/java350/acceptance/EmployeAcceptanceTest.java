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
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Step("Soit un employé appelé <prenom> <nom> de matricule <matricule>")
    public void insertEmploye(String prenom, String nom, String matricule) throws EmployeException {
        employeRepository.save(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));
    }

    @Step("Soit un employé appelé <prenom> <nom> de matricule <matricule> avec une perf de <perf>")
    public void insertEmployewithperf(String prenom, String nom, String matricule, Integer perf) throws EmployeException {
        employeRepository.save(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, perf, 1.0));
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

    @Step("Je calcule la performance du commercial de matricule <matricule>, il a traité un ca de <caTraite>. Son objectif était <objectifCa> !")
    public void checkCalculdePerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException{
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
    }

    @Step("J'obtiens bien la nouvelle performance de mon commercial de matricule <matricule> qui est de <performance>.")
    public void checkPerformanceCommercialIsCorrect(String matricule, Integer performance) throws EmployeException{
        Employe e = employeRepository.findByMatricule(matricule);
        Assertions.assertEquals(performance,e.getPerformance());
    }

}
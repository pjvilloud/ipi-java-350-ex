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
public class EmployeAcceptanceTest {

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

    @Step("On a actuellement 5 employés dans l'entrprise")
    public void mockData() {
        employeRepository.save(new Employe("Un", "Mélo", "T11111", LocalDate.now(), 1500d, 1, 1.1d));
        employeRepository.save(new Employe("Deux", "Mélo", "M11111", LocalDate.now(), 5000d, 1, 0.5d));
        employeRepository.save(new Employe("Trois", "Mélo", "C11111", LocalDate.now(), 1500d, 1, 1d));
        employeRepository.save(new Employe("Quatre", "Mélo", "C22222", LocalDate.now(), 2000d, 2, 1.1d));
        employeRepository.save(new Employe("Cinq", "Mélo", "C33333", LocalDate.now(), 1500d, 2, 0.5d));

    }

    @Step("Je calcule la performance de mon commercial <matricule> avec un CA de <ca> euros pour un objectif de <objectif>")
    public void calculAvgPerformance(String matricule, Long ca, Long objectif) throws EmployeException{
        employeService.calculPerformanceCommercial(matricule, ca, objectif);
    }

    @Step("J'obtiens une performance de <performance> sur mon commercial au matricule <matricule>")
    public void assertPerformance(Integer performance, String matricule) {
        Assertions.assertEquals(performance, employeRepository.findByMatricule(matricule).getPerformance());
    }
}
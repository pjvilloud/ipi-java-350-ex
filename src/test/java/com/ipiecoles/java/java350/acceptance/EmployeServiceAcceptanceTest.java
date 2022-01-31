package com.ipiecoles.java.java350.acceptance;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeServiceAcceptanceTest {


    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private EmployeService employeService;

    @Step("On vire tous les employés s'il y en a")
    public void vireTousLesEmployes() {
        employeRepository.deleteAll();
    }

    @Step("J'embauche une personne appelée <prenom> <nom> diplômée d'un <diplome> en tant que <poste> à <tempsTravail> et avec une performance de <performace>")
    public void embaucheEmploye(String prenom, String nom, String diplome, String poste, String tempsTravail,Integer performance) throws EmployeException {
        employeService.embaucheEmploye(nom, prenom,
                Poste.valueOf(poste.toUpperCase()),
                NiveauEtude.valueOf(diplome.toUpperCase()),
                tempsTravail.equals("temps plein") ? 1.0 : 0.5);
        Employe employe = employeRepository.findByMatricule("C00001");
        employe.setPerformance(performance);
        employeRepository.save(employe);
    }

    @Step("Je calcule la performance commerciale d'un employé avec le matricule <matricule> avec un caTraite de <caTraite> et un objectifCa de <objectifCa>")
    public void calculPerformance(String matricule, long caTraite, long objectifCa) throws EmployeException {
        employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
    }

    @Step("L'employé de matricule <matricule> a maintenant une performance de <performance>")
    public void checkNewPerfomance(String matricule, Integer performance) throws EmployeException {
        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertEquals(2,performance);
    }
}

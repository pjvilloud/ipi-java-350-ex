package com.ipiecoles.java.java350.tests;

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

    @Step("On supprime tout les employés")
    public void deleteAllEmployes() {
        employeRepository.deleteAll();
    }

    @Step("On embauche une personne appelée <pPrenom> <pNom> diplômée d'un <pDiplome> en tant que <pPoste> à <pTempsTravail> et qui a une performance de <pPerformance>")
    public void embaucheEmploye(String pPrenom, String pNom, String pDiplome, String pPoste, String pTempsTravail,Integer pPerformance) throws EmployeException {
        employeService.embaucheEmploye(pNom, pPrenom,
                Poste.valueOf(pPoste.toUpperCase()),
                NiveauEtude.valueOf(pDiplome.toUpperCase()),
                pTempsTravail.equals("temps plein") ? 1.0 : 0.5);
        Employe e1 = employeRepository.findByMatricule("C00001");
        e1.setPerformance(pPerformance);
        employeRepository.save(e1);
    }

    @Step("On calcule la performance commerciale d'un employé avec son matricule <pMatricule> avec le Chiffe d'Affaire Traite de <pCaTraite> et l'objectif de Chiffre d'Affaire de <pObjectifCa>")
    public void calculPerformance(String pMatricule, long pCaTraite, long pObjectifCa) throws EmployeException {
        employeService.calculPerformanceCommercial(pMatricule,pCaTraite,pObjectifCa);
    }

    @Step("L'employé de matricule <pMatricule> a maintenant une performance de <pPerformance>")
    public void checkNewPerfomance(String pMatricule, Integer pPerformance) throws EmployeException {
        Employe e1 = employeRepository.findByMatricule(pMatricule);
        Assertions.assertEquals(2,pPerformance);
    }

}
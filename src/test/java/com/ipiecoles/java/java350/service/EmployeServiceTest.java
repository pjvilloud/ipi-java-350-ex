package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @Mock
    EmployeRepository employeRepository;

    @InjectMocks
    EmployeService employeService;

    @Test
    public void embaucheEmployeTest() throws EmployeException {
    String nom = "Name";
    String prenom = "Prenom";
    Poste poste = Poste.TECHNICIEN;
    NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
    Double tempsPartiel = 1.0;

    when(employeRepository.findLastMatricule()).thenReturn(null);
    when(employeRepository.findByMatricule("00001")).thenReturn(null);

    employeService.embaucheEmploye(nom, prenom, poste, niveauEtude,tempsPartiel);
    }

    }

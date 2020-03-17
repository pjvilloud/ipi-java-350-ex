package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryTest {

	/*********************************************
	 * EXERCICE
	 *********************************************/
	@Autowired
	EmployeRepository employeRepository;

	@Autowired
	EmployeService employeService;

	/**
	 * cette methode supprime toute les données de BD avant d'exécuter le test
	 */
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
		// l'appel à la mthode insertDate pour insérer les données
		insertData();
	}

	/**
	 * l'insertion das donnée dans la base de donnée en mémoire
	 */
	public void insertData() {

		Employe emp1 = employeRepository
				.save(new Employe("emp1", "comer", "C55555", LocalDate.now().minusMonths(10), 2500.00, 1, 0.50));
		Employe emp2 = employeRepository
				.save(new Employe("emp2", "tech", "T99999", LocalDate.now().minusYears(2), 2500.00, 3, 4.00));
		Employe emp3 = employeRepository
				.save(new Employe("emp3", "comer", "C33333", LocalDate.now().minusMonths(8), 2500.00, 2, 1.00));
		Employe emp4 = employeRepository
				.save(new Employe("emp4", "comer", "C45876", LocalDate.now().minusMonths(8), 2500.00, 3, 0.5));
		Employe emp5 = employeRepository
				.save(new Employe("emp5", "TypeX", "X55555", LocalDate.now().minusMonths(10), 2500.00, 3, 0.50));
		Employe emp6 = employeRepository
				.save(new Employe("emp6", "TypeA", "A33333", LocalDate.now().minusMonths(8), 2500.00, 3, 1.00));

	}

	/**
	 * cette méthode vérifie que la méthode retourne null s'il n'existe pas de
	 * donnés dans la base.
	 */
	@Test
	public void testFindByImmatNull() {
		// Given
		employeRepository.deleteAll();
		// When
		String result = employeRepository.findLastMatricule();

		// Then
		Assertions.assertThat(result).isNull();
	}

	@Test
	public void testFindByImmat() {
		// Given
		// les paramètres sont initilisés dans la méthode BeforAll

		// When
		String result = employeRepository.findLastMatricule();

		// Then
		Assertions.assertThat(result).isEqualTo("99999");
	}

	@Test
	public void calculePerformaceTest() throws EmployeException {

		// Given
		// les paramètres sont initilisés dans la méthode BeforAll

		// When
		/**
		 * Cette variable contient le résulat de la méthode
		 * avgPerformanceWhereMatriculeStartsWith qui trouve la pérformance moyenne pour
		 * un type d'employé donné
		 */
		Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

		/**
		 * Cette méthode fixe la performance de la Commerçant en fonction de son
		 * CaObjective
		 */
		employeService.calculPerformanceCommercial("C33333", 115000L, 100000L);

		/*
		 * Ici, elle va trouver le comerçant C33333, qui a comme capitale 115000 et que
		 * son caObjective était 100000. la méthode donc va initializer sa pérformance à
		 * 4. parce qu'il a déja une pérformance égale à 2 et comme il a attente à son
		 * objectif (sup +5), il gagne performance +1 et commme la pérformance moyenne
		 * est 2 et qu'il a déja 3, donc supérieur à performance moyenne, il gagne
		 * encore +1. Au total, son pérformance doit être (2+1+1) = 4.
		 */
		Employe employeTrouve = employeRepository.findByMatricule("C33333");
		double empPerformanceAfterCalcule = employeTrouve.getPerformance();

		// Then
		/*
		 * Vérification de l'exactitude du réusltat près avoir exécutée les méthodes -
		 * calculPerformanceCommercial - avgPerformanceWhereMatriculeStartsWith
		 */
		Assertions.assertThat(performanceMoyenne).isEqualTo(2);
		Assertions.assertThat(empPerformanceAfterCalcule).isEqualTo(4);

	}
}

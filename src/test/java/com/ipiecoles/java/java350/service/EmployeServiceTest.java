package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

/*
 * Méthode dans laquelle on simule l'utilisation de dépendances extérieures via des mocks (ici dépendance = repo).
 * Permet de rester dans un test unitaire (si échec, c'est forcément dû au contenu de la méthode)
 * Simuler les dépendances permet de tester les méthodes d'un service par ex avant qu'une méthode ait été codée dans le repo.
 * Il faudra ensuite un test d'intégration (sur l'assemblage des modules, ici le repo et le service)
 * Pas besoin de @Autowired dans le cas d'un mock : on utilise plutôt @InjectMocks + @Mock pour chaque dépendance
 */

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

	@InjectMocks
	private EmployeService employeService;

	@Mock
	private EmployeRepository employeRepository;

	@Test
	public void testEmbaucheEmployeCommercialTempsPleinBTS() throws EmployeException {

		// Given

		String nom = "Doe";
		String prenom = "Jane";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		// On simule une base contenant déjà des employés
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
		// A partir de ce dernier mat artificiel, on incrémente pour créer un nouveau
		// commercial
		Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

		// When

		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		// Comme ce test n'est pas supposé lever d'exception, on ajoute un throw au
		// niveau de la signature (et non un try/catch)

		// Then

		// On utilise un capteur d'arguments pour récupérer les valeurs des arguments au
		// moment du save
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());

		Employe employe = employeCaptor.getValue();

		Assertions.assertThat(employe.getNom()).isEqualTo(nom);
		Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
		Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");

		// Pour le salaire on fait le calcul salaire_base * coeff selon nivEtude plutôt
		// que d'utiliser la formule dans le code
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);

		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		/*
		 * Assertions.assertThat(employe.getDateEmbauche())
		 * .format(DateTimeFormatter.ofPatterne("yyyyMMdd")) ).isEqualTo(
		 * LocalDate.now().format(DateTimeFormatter.ofPattern("yyyMMdd")));
		 */
		Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);

		// Autre option
		// Employe employeVerif = new Employe(nom, prenom, ...);
		// Assertions.assertThat(employe).isEqualTo(employeVerif);

	}

	@Test
	public void testEmbaucheLimiteMatricule() throws EmployeException {

		// Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.TECHNICIEN;
		NiveauEtude niveauEtude = NiveauEtude.CAP;
		Double tempsPartiel = 1.0;
		// On simule une base contenant déjà des employés
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

		// When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail("Aurait dû échouer car on visait la levée d'une exception avec ce test.");
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}

		/*
		 * Autre option pour remplacer le try / catch en utilisant les lambdas:
		 * Assertions.assertThatThrownBy(() -> {employeService.embaucheEmploye(nom,
		 * prenom, poste, niveauEtude, tempsPartiel);})
		 * .isInstanceOf(EmployeException.class).
		 * hasMessage("Limite des 100000 matricules atteinte !")
		 */

	}

}

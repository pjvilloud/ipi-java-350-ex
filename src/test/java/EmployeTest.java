import static org.mockito.Mockito.never;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.model.Employe;

public class EmployeTest {

	@Test
	public void testGetNombreAnneeAnciennete() {
		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));

		// When
		Integer anneeAncianite = employe.getNombreAnneeAnciennete();

		// then
		Assertions.assertThat(anneeAncianite).isEqualTo(2);
	}

	@Test
	public void testGetNombreAnneeAncienneteNplus2() {
		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));

		// When
		Integer anneeAncianite = employe.getNombreAnneeAnciennete();

		// then
		Assertions.assertThat(anneeAncianite).isEqualTo(0);
	}

	@Test
	public void testGetNombreAnneeAncienneteNull() {
		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// When
		Integer anneeAncianite = employe.getNombreAnneeAnciennete();

		// then
		Assertions.assertThat(anneeAncianite).isEqualTo(0);
	}

	@Test
	public void testGetNombreAnneeAnciennetenow() {
		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now());

		// When
		Integer anneeAncianite = employe.getNombreAnneeAnciennete();

		// then
		Assertions.assertThat(anneeAncianite).isEqualTo(0);
	}

	@ParameterizedTest
	@CsvSource({ "'M11111',2,1,1,1900", "'M11111',2,0.5,1,950", "'M11111',0,1,1,1700", "'M11111',2,0.5,1,950", "'T11111',2,0.5,1,600",
			"'T11111',4,1,1,1400", "'T11111',4,1,2,2700", "'T11111',0,1,1,1000", "'T11111',-2,1,1,1000",
			"'T11111',-2,0.5,1,500", "'T11111',-2,0.5,0,150", ",1,1,1,1100", ",0,2,1,1000" })
	void testGetPrimeAnnuelle(String matricule, Integer ancianite, Double tempsPartiel, Integer performance,
			Double prime) {
		// Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setDateEmbauche(LocalDate.now().minusYears(ancianite));
		employe.setPerformance(performance);
		employe.setTempsPartiel(tempsPartiel);

		// When
		Double primeCalculer = employe.getPrimeAnnuelle();

		// Then
		Assertions.assertThat(primeCalculer).isEqualTo(prime);
	}

}

package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    public void testCalculPerformanceCommercialCaNull() throws EmployeException {

        //Given
        String matricule = "C00001";
        Long caTraite = null;
        Long objectifCa = 2000L;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car le CA indiqué est nul.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaires traité ne peut être négatif ou nul !");
        }
       
    }
	
	/*
	 * Premiers tests sur méthode calculPerformanceCommercial dans les cas où on a une erreur de saisie
	 */
	
	@Test
    public void testCalculPerformanceCommercialCaNegatif() throws EmployeException {

        //Given
        String matricule = "C00001";
        Long caTraite = -1000L;
        Long objectifCa = 2000L;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car le CA indiqué est négatif.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaires traité ne peut être négatif ou nul !");
        }
       
    }
	
	@Test
    public void testCalculPerformanceCommercialObjectifCaNull() throws EmployeException {

        //Given
        String matricule = "C00001";
        Long caTraite = 1000L;
        Long objectifCa = null;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car l'objectif de CA indiqué est nul.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaires ne peut être négatif ou nul !");
        }
        
    }
	
	@Test
    public void testCalculPerformanceCommercialObjectifCaNegatif() throws EmployeException {

        //Given
        String matricule = "C00001";
        Long caTraite = 1000L;
        Long objectifCa = -1000L;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car l'objectif de CA indiqué est négatif.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaires ne peut être négatif ou nul !");
        }
        
    }

	@Test
    public void testCalculPerformanceCommercialMatriculeManager() throws EmployeException {

        //Given
        String matricule = "M00001";
        Long caTraite = 1000L;
        Long objectifCa = 1000L;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car le matricule ne correspond pas à un commercial.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être nul et doit commencer par un C !");
        }
        
    }
	
	@Test
    public void testCalculPerformanceCommercialMatriculeNull() throws EmployeException {

        //Given
        String matricule = null;
        Long caTraite = 1000L;
        Long objectifCa = 1000L;

        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car le matricule ne correspond pas à un commercial.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être nul et doit commencer par un C !");
        }
        
    }
	
	@Test
    public void testCalculPerformanceCommercialEmployeNotFound(){
        
		//Given
        String matricule = "C00001";
        Long caTraite = 1000L;
        Long objectifCa = 1000L;
        // On simule la recherche vaine de l'employé dans la base
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);
      
        //When
        try {
        	employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        	Assertions.fail("Le test aurait dû échouer car l'employé n'existe pas.");
        } catch (Exception e) {
        	//Then
        	Assertions.assertThat(e).isInstanceOf(EmployeException.class);
        	Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule " + matricule + " n'existe pas !");
        }
    }
	
	
	/**
     * Test paramétré de la méthode calculPerformanceCommercial pour les cas 1 à 5
     */
	
    @ParameterizedTest
    @CsvSource({
    		// Cas 1 : Un CA inférieur de plus de 20% à l'objectif fixé implique un retour à la performance de base
            "7000, 10000, 'C00001', 5, 10, 1",
            // Cas 2 : Un CA inférieur entre 20% et 5% à l'objectif fixé implique une perte de 2 points de performance (+1pt si perf > perf moyenne)
            "9000, 10000, 'C00002', 5, 10, 3",
            // Cas 3 : Un CA situé entre -5% et +5% par rapport à l'objectif fixé n'implique aucun changement (sauf si perf > perf moyenne)
            "9750 , 10000, 'C00003', 5, 10, 5",
            // Cas 4 : Un CA supérieur de 5% à 20% à l'objectif fixé implique un gain d'1 point de performance (+1pt si perf > perf moyenne)
            "11000, 10000, 'C00004', 5, 10, 6",
            // Cas 5 : Un CA supérieur de plus de 20% à l'objectif fixé implique un gain de 4 points de performance (+1pt si perf > perf moyenne)
            "15000, 10000, 'C00005', 11, 10, 16"
    })
    public void testParamCalculPerformanceCommercial (Long caTraite, Long objectifCa, String matricule, Integer initialPerf, Double avgPerf, Integer finalPerf) throws EmployeException {
        
    	// Given
    	
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(initialPerf);

        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(avgPerf);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);

        // When
    
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        // Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
        Employe employeWithNewPerf = employeArgumentCaptor.getValue();
        Assertions.assertThat(employeWithNewPerf.getPerformance()).isEqualTo(finalPerf);
    }
	

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
		/* Autre option
		 * Assertions.assertThat(employe.getDateEmbauche())
		 * .format(DateTimeFormatter.ofPatterne("yyyyMMdd")) ).isEqualTo(
		 * LocalDate.now().format(DateTimeFormatter.ofPattern("yyyMMdd")));
		 */
		Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);

		/* Autre option
		 * Employe employeVerif = new Employe(nom, prenom, ...);
		 * Assertions.assertThat(employe).isEqualTo(employeVerif);
		 */

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

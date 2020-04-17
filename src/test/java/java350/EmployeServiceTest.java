package java350;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityExistsException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.repository.CrudRepository;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;


@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {
	
	@InjectMocks
	EmployeService employeService;
	
	@Mock
	EmployeRepository employeRepository;
	
	//@BeforeEach
	//MockitoAnnotations.
	  @Test
	    public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
	        //Given
	        String nom = "Doe";
	        String prenom = "John";
	        Poste poste = Poste.TECHNICIEN;
	        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
	        Double tempsPartiel = 1.0;
	        when(employeRepository.findLastMatricule()).thenReturn("00345");
	        when(employeRepository.findByMatricule("T00346")).thenReturn(null);

	        //When
	        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

	        //Then
	        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
	        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
	        org.junit.jupiter.api.Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
	        org.junit.jupiter.api.Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
	        org.junit.jupiter.api.Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	        org.junit.jupiter.api.Assertions.assertEquals("T00346", employeArgumentCaptor.getValue().getMatricule());
	        org.junit.jupiter.api.Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

	        //1521.22 * 1.2 * 1.0
	        org.junit.jupiter.api.Assertions.assertEquals(1825.46, employeArgumentCaptor.getValue().getSalaire().doubleValue());
	    }

	    private CrudRepository<Employe, Long> verify(EmployeRepository employeRepository2, Object times) {
		// TODO Auto-generated method stub
		return null;
	}

		private Object times(int i) {
		// TODO Auto-generated method stub
		return null;
	}

		@Test
	    public void testEmbaucheEmployeManagerMiTempsMaster() throws EmployeException {
	        //Given
	        String nom = "Doe";
	        String prenom = "John";
	        Poste poste = Poste.MANAGER;
	        NiveauEtude niveauEtude = NiveauEtude.MASTER;
	        Double tempsPartiel = 0.5;
	        when(employeRepository.findLastMatricule()).thenReturn("00345");
	        when(employeRepository.findByMatricule("M00346")).thenReturn(null);

	        //When
	        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

	        //Then
	        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
	        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
	        org.junit.jupiter.api.Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
	        org.junit.jupiter.api.Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
	        org.junit.jupiter.api.Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	        org.junit.jupiter.api.Assertions.assertEquals("M00346", employeArgumentCaptor.getValue().getMatricule());
	        org.junit.jupiter.api.Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

	        //1521.22 * 1.4 * 0.5
	        org.junit.jupiter.api.Assertions.assertEquals(1064.85, employeArgumentCaptor.getValue().getSalaire().doubleValue());
	    }


		private OngoingStubbing<String> when(String findLastMatricule) {
			// TODO Auto-generated method stub
			return null;
		}

		@Test
	    public void testEmbaucheEmployeManagerMiTempsMasterNoLastMatricule() throws EmployeException {
	        //Given
	        String nom = "Doe";
	        String prenom = "John";
	        Poste poste = Poste.MANAGER;
	        NiveauEtude niveauEtude = NiveauEtude.MASTER;
	        Double tempsPartiel = 0.5;
	        when(employeRepository.findLastMatricule()).thenReturn(null);
	        when(employeRepository.findByMatricule("M00001")).thenReturn(null);

	        //When
	        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

	        //Then
	        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
	        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
	        org.junit.jupiter.api.Assertions.assertEquals("M00001", employeArgumentCaptor.getValue().getMatricule());
	    }

	    private OngoingStubbing<String> when(Employe findByMatricule) {
			// TODO Auto-generated method stub
			return null;
		}

		@Test
	    public void testEmbaucheEmployeManagerMiTempsMasterExistingEmploye(){
	        //Given
	        String nom = "Doe";
	        String prenom = "John";
	        Poste poste = Poste.MANAGER;
	        NiveauEtude niveauEtude = NiveauEtude.MASTER;
	        Double tempsPartiel = 0.5;
	        when(employeRepository.findLastMatricule()).thenReturn(null);
	        when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

	        //When/Then
	        EntityExistsException e = org.junit.jupiter.api.Assertions.assertThrows(EntityExistsException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
	        org.junit.jupiter.api.Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());
	    }

	    @Test
	    public void testEmbaucheEmployeManagerMiTempsMaster99999(){
	        //Given
	        String nom = "Doe";
	        String prenom = "John";
	        Poste poste = Poste.MANAGER;
	        NiveauEtude niveauEtude = NiveauEtude.MASTER;
	        Double tempsPartiel = 0.5;
	        when(employeRepository.findLastMatricule()).thenReturn("99999");

	        //When/Then
	        EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
	        org.junit.jupiter.api.Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
	    }
	
	@Test
	void embaucheEmploye() throws EmployeException {
		//given (qd la methode findlastmatricule va etre appelee on veut qu'elle renvoie null pour 
		//simuler une base de donnée employe vide
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		//qd on va chercher si l'employé ac le matricule calculé existe, on veut que la methode renvoie null
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		//WHEN
		employeService.embaucheEmploye("doe", "john", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);
		
		//Then
		
	}
	
	@Test
	void embaucheEmployeXEmploye() throws EmployeException {
		//given (qd la methode findlastmatricule va etre appelee on veut qu'elle renvoie null pour 
		//simuler une base de donnée employe vide
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
		//qd on va chercher si l'employé ac le matricule calculé existe, on veut que la methode renvoie null
		Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);
		//peut definir variable pour assertions
		Poste poste = Poste.MANAGER;
		
		//WHEN
		employeService.embaucheEmploye("doe", "john", poste, NiveauEtude.LICENCE, 1.0);
		
		
		
		//Then
		//Employe employe = employeRepository.findByMatricule("M45679");
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		//on vérifie qu ela methode save a bien été appelée sue employeRepository, et on capture le parametre
		Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
		Employe employe = employeArgumentCaptor.getValue();
		Assertions.assertThat(employe.getNom()).isEqualTo("doe");
		Assertions.assertThat(employe.getPrenom()).isEqualTo("john");
		Assertions.assertThat(employe.getMatricule()).isEqualTo("M45679");
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
		Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		
		//calcule du salaire
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
		
	}
	
	void testExceptionNormal(){
	    //Given
	    try {
	        //When
	        employeService.embaucheEmploye(null, null, null, null, null);
	        Assertions.fail("Aurait du lancer une exception");
	    } catch (Exception e){
	        //Then
	        //Vérifie que l'exception levée est de type EmployeException
	        Assertions.assertThat(e).isInstanceOf(EmployeException.class);
	        //Vérifie le contenu du message
	        Assertions.assertThat(e.getMessage()).isEqualTo("Message de l'erreur");
	    }
	}
	//gerer test exception methode2
	void testExceptionJava8(){
	    //Given
	    Assertions.assertThatThrownBy(() -> {
	        //When
	        employeService.embaucheEmploye(null, null, null, null, null);
	    })//Then
	            .isInstanceOf(EmployeException.class).hasMessage("Message de l'erreur");
	}
	
	
	//test intégration 1ier point
	 @ParameterizedTest
	   @CsvSource({"'C00011', 2000, 2500"})
	 public void calculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) {
		 //given
		 Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);

		 //when
	 EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () ->  employeService.calculPerformanceCommercial(matricule,caTraite , objectifCa));
	
		 //then
	 



	 org.junit.jupiter.api.Assertions.assertEquals(e.getMessage(), "Le matricule C00011 n'existe pas !");
		 
	 }
	 
	 
	 
	 
	
}

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteNow() {
        //Given = Initialisation des données d'entrée
        LocalDate dateEmbauche = LocalDate.now();
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbauche);



        //When = Exécution de la méthode à tester
        //Toujours mettre 1 seul test
        Integer nbAnnee = employe.getNombreAnneeAnciennete();


        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnee).isGreaterThanOrEqualTo(5);

    }
}
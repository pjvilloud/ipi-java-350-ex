import com.ipiecoles.java.java350.model.Employe;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class StepImplementation {

    private HashSet<Character> vowels;

    @Step("Vowels in English language are <vowelString>.")
    public void setLanguageVowels(String vowelString) {
        vowels = new HashSet<>();
        for (char ch : vowelString.toCharArray()) {
            vowels.add(ch);
        }
    }

    @Step("The word <word> has <expectedCount> vowels.")
    public void verifyVowelsCountInWord(String word, int expectedCount) {
        int actualCount = countVowels(word);
        assertThat(expectedCount).isEqualTo(actualCount);
    }

    @Step("Almost all words have vowels <wordsTable>")
    public void verifyVowelsCountInMultipleWords(Table wordsTable) {
        for (TableRow row : wordsTable.getTableRows()) {
            String word = row.getCell("Word");
            int expectedCount = Integer.parseInt(row.getCell("Vowel Count"));
            int actualCount = countVowels(word);

            assertThat(expectedCount).isEqualTo(actualCount);
        }
    }

    private int countVowels(String word) {
        int count = 0;
        for (char ch : word.toCharArray()) {
            if (vowels.contains(ch)) {
                count++;
            }
        }
        return count;
    }

    Employe employe;

    @Step("Soit un employé embauché cette année")
    public void createEmploye() {
        employe = new Employe("Doe", "John", "M12345", LocalDate.now(), 2500.0, 1, 1.0);
    }

    Integer nbAnneesAnciennete;

    @Step("Lorsque je calcule son nombre d'années d'ancienneté")
    public void getNbAnneesAnciennete() {
        nbAnneesAnciennete = employe.getNombreAnneeAnciennete();
    }

    @Step("J'obtiens <ancienneteCalculee>")
    public void verifAnciennete(Integer ancienneteCalculee) {
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(ancienneteCalculee);
    }

    @Step("Soit un employé embauché il y a <nbAnneesEmbauche> an")
    public void creerEmploye2(Integer nbAnneesEmbauche) {
        employe = new Employe("Doe", "John", "M12345", LocalDate.now().minusYears(nbAnneesEmbauche), 2500.0, 1, 1.0);
    }

    @Step("Soit un employé embauché dans <nbAnneesAvantEmbauche> ans")
    public void creerEmploye3(Integer nbAnneesAvantEmbauche) {
        employe = new Employe("Doe", "John", "M12345", LocalDate.now().plusYears(nbAnneesAvantEmbauche), 2500.0, 1, 1.0);
    }
}

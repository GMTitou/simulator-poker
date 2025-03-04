import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

public class PokerSimulatorIntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Test d'intégration du flux complet avec deux joueurs")
    public void testFullSimulationFlow() {
        String input = "1COEUR 2PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "6COEUR 7PIQUE 8CARREAU 9TREFLE 10COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Le test d'intégration a échoué avec l'exception: " + e.getMessage());
        }

        String output = outContent.toString();
        assertTrue(output.contains("Veuillez choisir les carte du Joueur 1"));
        assertTrue(output.contains("Veuillez maintenant choisir les carte du Joueur 2"));
        assertTrue(output.contains("1 de COEUR"));
        assertTrue(output.contains("10 de COEUR"));
        assertTrue(output.contains("Premier joueur a la carte la plus haute") ||
                output.contains("Deuxième joueur a la carte la plus haute") ||
                output.contains("Points du premier joueur") ||
                output.contains("Points du deuxième joueur"));
    }

    @Test
    @DisplayName("Test d'intégration avec carte invalide puis valide")
    public void testInvalidCardThenValid() {
        String input = "14COEUR 2PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "1COEUR 2PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "6COEUR 7PIQUE 8CARREAU 9TREFLE 10COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Le test d'intégration a échoué avec l'exception: " + e.getMessage());
        }

        String output = outContent.toString();
        assertTrue(output.contains("Erreur: Le numéro doit être entre 1 (As) et 13 (Roi)"));
        assertTrue(output.contains("1 de COEUR"));
        assertTrue(output.contains("6 de COEUR"));
    }

    @Test
    @DisplayName("Test d'intégration avec tentative de carte en double entre joueurs")
    public void testDuplicateCardBetweenPlayers() {
        String input = "1COEUR 2PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "1COEUR 7PIQUE 8CARREAU 9TREFLE 10COEUR\n" +
                "6COEUR 7PIQUE 8CARREAU 9TREFLE 10COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Le test d'intégration a échoué avec l'exception: " + e.getMessage());
        }

        String output = outContent.toString();
        assertTrue(output.contains("Cette carte est déjà prise par un autre joueur"));
        assertTrue(output.contains("6 de COEUR"));
    }

    @Test
    @DisplayName("Test d'intégration avec différentes combinaisons de mains")
    public void testDifferentHandCombinations() {
        String input = "1COEUR 1PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "6COEUR 7PIQUE 8CARREAU 9TREFLE 13COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Le test d'intégration a échoué avec l'exception: " + e.getMessage());
        }

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède une paire"));

        assertTrue(output.contains("Points du premier joueur: 2"));
    }

    @Test
    @DisplayName("Test d'intégration avec deux joueurs ayant des paires")
    public void testBothPlayersWithPairs() {
        String input = "1COEUR 1PIQUE 3CARREAU 4TREFLE 5COEUR\n" +
                "6COEUR 7PIQUE 7CARREAU 9TREFLE 10COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Le test d'intégration a échoué avec l'exception: " + e.getMessage());
        }

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède une paire"));
        assertTrue(output.contains("Deuxième joueur possède une paire"));

        assertTrue(output.contains("Points du premier joueur: 2"));
        assertTrue(output.contains("Points du deuxième joueur: 2"));
    }
}
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec brelan (triple)")
    public void testCalculatorPointBrelan() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(7, Color.COEUR));
        cartesJoueur1.add(new Card(7, Color.PIQUE));
        cartesJoueur1.add(new Card(7, Color.CARREAU));
        cartesJoueur1.add(new Card(4, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(10, Color.COEUR));
        cartesJoueur2.add(new Card(10, Color.PIQUE));
        cartesJoueur2.add(new Card(10, Color.CARREAU));
        cartesJoueur2.add(new Card(2, Color.TREFLE));
        cartesJoueur2.add(new Card(3, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède un triple: [7]"));
        assertTrue(output.contains("Deuxième joueur possède un triple: [10]"));

        assertTrue(output.contains("Points du premier joueur: 3"));
        assertTrue(output.contains("Points du deuxième joueur: 3"));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec carré")
    public void testCalculatorPointCarre() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(8, Color.COEUR));
        cartesJoueur1.add(new Card(8, Color.PIQUE));
        cartesJoueur1.add(new Card(8, Color.CARREAU));
        cartesJoueur1.add(new Card(8, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(10, Color.COEUR));
        cartesJoueur2.add(new Card(10, Color.PIQUE));
        cartesJoueur2.add(new Card(10, Color.CARREAU));
        cartesJoueur2.add(new Card(2, Color.TREFLE));
        cartesJoueur2.add(new Card(3, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède un carré: [8]"));

        assertTrue(output.contains("Deuxième joueur possède un triple: [10]"));

        assertTrue(output.contains("Points du premier joueur: 7"));
        assertTrue(output.contains("Points du deuxième joueur: 3"));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec couleur")
    public void testCalculatorPointCouleur() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(2, Color.COEUR));
        cartesJoueur1.add(new Card(5, Color.COEUR));
        cartesJoueur1.add(new Card(7, Color.COEUR));
        cartesJoueur1.add(new Card(9, Color.COEUR));
        cartesJoueur1.add(new Card(11, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(10, Color.COEUR));
        cartesJoueur2.add(new Card(10, Color.PIQUE));
        cartesJoueur2.add(new Card(10, Color.CARREAU));
        cartesJoueur2.add(new Card(2, Color.TREFLE));
        cartesJoueur2.add(new Card(3, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une couleur: COEUR"));

        assertTrue(output.contains("Points du premier joueur: 5"));
        assertTrue(output.contains("Points du deuxième joueur: 3"));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec full house (brelan + paire)")
    public void testCalculatorPointFullHouse() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(7, Color.COEUR));
        cartesJoueur1.add(new Card(7, Color.PIQUE));
        cartesJoueur1.add(new Card(7, Color.CARREAU));
        cartesJoueur1.add(new Card(4, Color.TREFLE));
        cartesJoueur1.add(new Card(4, Color.PIQUE));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(10, Color.COEUR));
        cartesJoueur2.add(new Card(10, Color.PIQUE));
        cartesJoueur2.add(new Card(2, Color.CARREAU));
        cartesJoueur2.add(new Card(2, Color.TREFLE));
        cartesJoueur2.add(new Card(3, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède un triple et une paire: [7] et [4]"));

        assertTrue(output.contains("Deuxième joueur possède deux paires: [2, 10]"));

        assertTrue(output.contains("Points du premier joueur: 6"));
        assertTrue(output.contains("Points du deuxième joueur: 4"));
    }

    @Test
    @DisplayName("Test de comparaison entre couleur et full house")
    public void testComparaisonCouleurVsFullHouse() {
        List<Card> cartesJoueur1 = new ArrayList<>(); // Couleur
        cartesJoueur1.add(new Card(2, Color.COEUR));
        cartesJoueur1.add(new Card(5, Color.COEUR));
        cartesJoueur1.add(new Card(7, Color.COEUR));
        cartesJoueur1.add(new Card(9, Color.COEUR));
        cartesJoueur1.add(new Card(11, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>(); // Full house
        cartesJoueur2.add(new Card(6, Color.COEUR));
        cartesJoueur2.add(new Card(6, Color.PIQUE));
        cartesJoueur2.add(new Card(6, Color.CARREAU));
        cartesJoueur2.add(new Card(3, Color.TREFLE));
        cartesJoueur2.add(new Card(3, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une couleur: COEUR"));
        assertTrue(output.contains("Deuxième joueur possède un triple et une paire: [6] et [3]"));

        assertTrue(output.contains("Points du premier joueur: 5"));
        assertTrue(output.contains("Points du deuxième joueur: 6"));
    }

    @Test
    @DisplayName("Test avec deux couleurs différentes")
    public void testDeuxCouleurs() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(2, Color.COEUR));
        cartesJoueur1.add(new Card(5, Color.COEUR));
        cartesJoueur1.add(new Card(7, Color.COEUR));
        cartesJoueur1.add(new Card(9, Color.COEUR));
        cartesJoueur1.add(new Card(11, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(4, Color.TREFLE));
        cartesJoueur2.add(new Card(6, Color.TREFLE));
        cartesJoueur2.add(new Card(8, Color.TREFLE));
        cartesJoueur2.add(new Card(10, Color.TREFLE));
        cartesJoueur2.add(new Card(12, Color.TREFLE));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une couleur: COEUR"));
        assertTrue(output.contains("Deuxième joueur a une couleur: TREFLE"));

        assertTrue(output.contains("Deuxième joueur a la carte la plus haute: 12"));

        assertTrue(output.contains("Points du premier joueur: 5"));
        assertTrue(output.contains("Points du deuxième joueur: 5"));
    }

    @Test
    @DisplayName("Test avec carré vs couleur")
    public void testCarreVsCouleur() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(8, Color.COEUR));
        cartesJoueur1.add(new Card(8, Color.PIQUE));
        cartesJoueur1.add(new Card(8, Color.CARREAU));
        cartesJoueur1.add(new Card(8, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(4, Color.TREFLE));
        cartesJoueur2.add(new Card(6, Color.TREFLE));
        cartesJoueur2.add(new Card(8, Color.TREFLE));
        cartesJoueur2.add(new Card(10, Color.TREFLE));
        cartesJoueur2.add(new Card(12, Color.TREFLE));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède un carré: [8]"));
        assertTrue(output.contains("Deuxième joueur a une couleur: TREFLE"));

        assertTrue(output.contains("Points du premier joueur: 7"));
        assertTrue(output.contains("Points du deuxième joueur: 5"));
    }
}
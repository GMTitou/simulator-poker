import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("Test de la méthode choisirCartesEnUneFois avec une saisie valide")
    public void testChoisirCartesEnUneFoisValide() {
        String input = "1COEUR 2PIQUE 3CARREAU 4TREFLE 5COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        List<Card> cartes = Main.choisirCartesEnUneFois(scanner, null);

        assertEquals(5, cartes.size());
        Assertions.assertEquals(1, cartes.get(0).getNumber());
        Assertions.assertEquals(Color.COEUR, cartes.get(0).getColor());
        Assertions.assertEquals(2, cartes.get(1).getNumber());
        Assertions.assertEquals(Color.PIQUE, cartes.get(1).getColor());
        Assertions.assertEquals(3, cartes.get(2).getNumber());
        Assertions.assertEquals(Color.CARREAU, cartes.get(2).getColor());
        Assertions.assertEquals(4, cartes.get(3).getNumber());
        Assertions.assertEquals(Color.TREFLE, cartes.get(3).getColor());
        Assertions.assertEquals(5, cartes.get(4).getNumber());
        Assertions.assertEquals(Color.COEUR, cartes.get(4).getColor());

        System.setIn(System.in);
    }

    @Test
    @DisplayName("Test de la méthode choisirCartesEnUneFois avec cartes à éviter")
    public void testChoisirCartesEnUneFoisAvecCartesAEviter() {
        List<Card> cartesAEviter = new ArrayList<>();
        cartesAEviter.add(new Card(1, Color.COEUR));
        cartesAEviter.add(new Card(2, Color.PIQUE));

        String input = "1COEUR 3PIQUE 4CARREAU 5TREFLE 6COEUR\n" +
                "7COEUR 8PIQUE 9CARREAU 10TREFLE 11COEUR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        List<Card> cartes = Main.choisirCartesEnUneFois(scanner, cartesAEviter);

        assertEquals(5, cartes.size());
        Assertions.assertEquals(7, cartes.get(0).getNumber());
        Assertions.assertEquals(Color.COEUR, cartes.get(0).getColor());

        System.setIn(System.in);
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec carte haute")
    public void testCalculatorPointCarteHaute() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(13, Color.COEUR));
        cartesJoueur1.add(new Card(2, Color.PIQUE));
        cartesJoueur1.add(new Card(3, Color.CARREAU));
        cartesJoueur1.add(new Card(4, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(12, Color.COEUR));
        cartesJoueur2.add(new Card(7, Color.PIQUE));
        cartesJoueur2.add(new Card(8, Color.CARREAU));
        cartesJoueur2.add(new Card(9, Color.TREFLE));
        cartesJoueur2.add(new Card(10, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        assertTrue(outContent.toString().contains("Premier joueur a la carte la plus haute: 13"));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec une paire")
    public void testCalculatorPointPaire() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(10, Color.COEUR));
        cartesJoueur1.add(new Card(10, Color.PIQUE));
        cartesJoueur1.add(new Card(3, Color.CARREAU));
        cartesJoueur1.add(new Card(4, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(12, Color.COEUR));
        cartesJoueur2.add(new Card(7, Color.PIQUE));
        cartesJoueur2.add(new Card(8, Color.CARREAU));
        cartesJoueur2.add(new Card(9, Color.TREFLE));
        cartesJoueur2.add(new Card(11, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        assertTrue(outContent.toString().contains("Premier joueur possède une paire: [10]"));
    }

    @Test
    @DisplayName("Test de la méthode calculatorPoint avec deux paires")
    public void testCalculatorPointDeuxPaires() {
        List<Card> cartesJoueur1 = new ArrayList<>();
        cartesJoueur1.add(new Card(10, Color.COEUR));
        cartesJoueur1.add(new Card(10, Color.PIQUE));
        cartesJoueur1.add(new Card(3, Color.CARREAU));
        cartesJoueur1.add(new Card(4, Color.TREFLE));
        cartesJoueur1.add(new Card(5, Color.COEUR));

        List<Card> cartesJoueur2 = new ArrayList<>();
        cartesJoueur2.add(new Card(12, Color.COEUR));
        cartesJoueur2.add(new Card(12, Color.PIQUE));
        cartesJoueur2.add(new Card(8, Color.CARREAU));
        cartesJoueur2.add(new Card(8, Color.TREFLE));
        cartesJoueur2.add(new Card(11, Color.COEUR));

        Main.calculatorPoint(cartesJoueur1, cartesJoueur2);

        assertTrue(outContent.toString().contains("Deuxième joueur possède deux paires: [8, 12]"));
    }
}
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PokerSimulatorIntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        outContent.reset();
    }

    private List<Card> createHand(int[] numbers, Color[] colors) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            hand.add(new Card(numbers[i], colors[i]));
        }
        return hand;
    }

    @Test
    @DisplayName("Test de détection de la quinte flush royale")
    public void testRoyalFlush() {
        List<Card> royalFlush = createHand(
                new int[]{10, 11, 12, 13, 1},
                new Color[]{Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR}
        );

        List<Card> ordinaryHand = createHand(
                new int[]{2, 5, 7, 9, 11},
                new Color[]{Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.PIQUE, Color.CARREAU}
        );

        Main.calculatorPoint(royalFlush, ordinaryHand);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une quinte flush royale"));
        assertTrue(output.contains("Points du premier joueur: 10"));
    }

    @Test
    @DisplayName("Test de détection de la quinte flush")
    public void testStraightFlush() {
        List<Card> straightFlush = createHand(
                new int[]{5, 6, 7, 8, 9},
                new Color[]{Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE}
        );

        List<Card> fourOfAKind = createHand(
                new int[]{7, 7, 7, 7, 2},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        Main.calculatorPoint(straightFlush, fourOfAKind);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une quinte flush"));
        assertTrue(output.contains("Points du premier joueur: 9"));
        assertTrue(output.contains("Deuxième joueur possède un carré"));
        assertTrue(output.contains("Points du deuxième joueur: 7"));
    }

    @Test
    @DisplayName("Test de détection de la quinte (suite)")
    public void testStraight() {
        List<Card> straight = createHand(
                new int[]{3, 4, 5, 6, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> flush = createHand(
                new int[]{2, 5, 7, 9, 11},
                new Color[]{Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE}
        );

        Main.calculatorPoint(straight, flush);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une suite (quinte)"));
        assertTrue(output.contains("Points du premier joueur: 8"));
        assertTrue(output.contains("Deuxième joueur a une couleur"));
        assertTrue(output.contains("Points du deuxième joueur: 5"));
    }

    @Test
    @DisplayName("Test de comparaison entre quinte flush royale et quinte flush")
    public void testRoyalFlushVsStraightFlush() {
        List<Card> royalFlush = createHand(
                new int[]{10, 11, 12, 13, 1},
                new Color[]{Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR}
        );

        List<Card> straightFlush = createHand(
                new int[]{5, 6, 7, 8, 9},
                new Color[]{Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE}
        );

        Main.calculatorPoint(royalFlush, straightFlush);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une quinte flush royale"));
        assertTrue(output.contains("Deuxième joueur a une quinte flush"));
        assertTrue(output.contains("Points du premier joueur: 10"));
        assertTrue(output.contains("Points du deuxième joueur: 9"));
    }

    @Test
    @DisplayName("Test de comparaison entre quinte flush et carré")
    public void testStraightFlushVsFourOfAKind() {
        List<Card> straightFlush = createHand(
                new int[]{5, 6, 7, 8, 9},
                new Color[]{Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE}
        );

        List<Card> fourOfAKind = createHand(
                new int[]{7, 7, 7, 7, 2},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        Main.calculatorPoint(straightFlush, fourOfAKind);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une quinte flush"));
        assertTrue(output.contains("Deuxième joueur possède un carré"));
        assertTrue(output.contains("Points du premier joueur: 9"));
        assertTrue(output.contains("Points du deuxième joueur: 7"));
    }

    @Test
    @DisplayName("Test complet de la hiérarchie des mains")
    public void testFullHierarchy() {
        List<Card> royalFlush = createHand(
                new int[]{10, 11, 12, 13, 1},
                new Color[]{Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR}
        );

        List<Card> straightFlush = createHand(
                new int[]{5, 6, 7, 8, 9},
                new Color[]{Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE, Color.TREFLE}
        );

        List<Card> fourOfAKind = createHand(
                new int[]{7, 7, 7, 7, 2},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> fullHouse = createHand(
                new int[]{6, 6, 6, 3, 3},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> flush = createHand(
                new int[]{2, 5, 7, 9, 11},
                new Color[]{Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE}
        );

        List<Card> straight = createHand(
                new int[]{3, 4, 5, 6, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> threeOfAKind = createHand(
                new int[]{9, 9, 9, 2, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> twoPairs = createHand(
                new int[]{10, 10, 4, 4, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> onePair = createHand(
                new int[]{10, 10, 2, 5, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> highCard = createHand(
                new int[]{13, 10, 8, 5, 2},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        Main.calculatorPoint(royalFlush, straightFlush);
        outContent.reset();

        Main.calculatorPoint(straightFlush, fourOfAKind);
        outContent.reset();

        Main.calculatorPoint(fourOfAKind, fullHouse);
        outContent.reset();

        Main.calculatorPoint(fullHouse, flush);
        outContent.reset();

        Main.calculatorPoint(flush, straight);
        outContent.reset();

        Main.calculatorPoint(straight, threeOfAKind);
        outContent.reset();

        Main.calculatorPoint(threeOfAKind, twoPairs);
        outContent.reset();

        Main.calculatorPoint(twoPairs, onePair);
        outContent.reset();

        Main.calculatorPoint(onePair, highCard);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur possède une paire"));
        assertTrue(output.contains("Points du premier joueur: 2"));
        assertTrue(output.contains("Deuxième joueur a la carte la plus haute"));
        assertTrue(output.contains("Points du deuxième joueur: 1"));
    }

    @Test
    @DisplayName("Test pour vérifier que isSequential fonctionne correctement")
    public void testIsSequential() {
        List<Card> normalStraight = createHand(
                new int[]{3, 4, 5, 6, 7},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        List<Card> notStraight = createHand(
                new int[]{3, 4, 6, 7, 9},
                new Color[]{Color.COEUR, Color.PIQUE, Color.CARREAU, Color.TREFLE, Color.COEUR}
        );

        Main.calculatorPoint(normalStraight, notStraight);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une suite (quinte)"));
        assertFalse(output.contains("Deuxième joueur a une suite (quinte)"));
    }

    @Test
    @DisplayName("Test pour vérifier que isRoyalFlush fonctionne correctement")
    public void testIsRoyalFlush() {
        List<Card> royalFlush = createHand(
                new int[]{10, 11, 12, 13, 1},
                new Color[]{Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR, Color.COEUR}
        );

        List<Card> aceStartStraightFlush = createHand(
                new int[]{1, 2, 3, 4, 5},
                new Color[]{Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE, Color.PIQUE}
        );

        Main.calculatorPoint(royalFlush, aceStartStraightFlush);

        String output = outContent.toString();

        assertTrue(output.contains("Premier joueur a une quinte flush royale"));
        assertTrue(output.contains("Deuxième joueur a une quinte flush"));
    }
}
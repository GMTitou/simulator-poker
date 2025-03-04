import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MainMethodsTest {

    @Test
    @DisplayName("Test de la méthode isSequential")
    public void testIsSequential() throws Exception {
        java.lang.reflect.Method isSequentialMethod = Main.class.getDeclaredMethod("isSequential", List.class);
        isSequentialMethod.setAccessible(true);

        List<Card> normalStraight = new ArrayList<>();
        normalStraight.add(new Card(3, Color.COEUR));
        normalStraight.add(new Card(4, Color.PIQUE));
        normalStraight.add(new Card(5, Color.CARREAU));
        normalStraight.add(new Card(6, Color.TREFLE));
        normalStraight.add(new Card(7, Color.COEUR));

        boolean isNormalStraight = (boolean) isSequentialMethod.invoke(null, normalStraight);

        assertTrue(isNormalStraight, "Une suite 3,4,5,6,7 devrait être détectée comme séquentielle");

        List<Card> notStraight = new ArrayList<>();
        notStraight.add(new Card(2, Color.COEUR));
        notStraight.add(new Card(3, Color.PIQUE));
        notStraight.add(new Card(5, Color.CARREAU));
        notStraight.add(new Card(9, Color.TREFLE));
        notStraight.add(new Card(10, Color.COEUR));

        boolean isNotStraight = (boolean) isSequentialMethod.invoke(null, notStraight);

        assertFalse(isNotStraight, "Une main 2,3,5,9,10 ne devrait pas être détectée comme séquentielle");
    }

    @Test
    @DisplayName("Test de la méthode isRoyalFlush")
    public void testIsRoyalFlush() throws Exception {
        java.lang.reflect.Method isRoyalFlushMethod = Main.class.getDeclaredMethod("isRoyalFlush", List.class);
        isRoyalFlushMethod.setAccessible(true);

        List<Card> royalFlush = new ArrayList<>();
        royalFlush.add(new Card(10, Color.COEUR));
        royalFlush.add(new Card(11, Color.COEUR));
        royalFlush.add(new Card(12, Color.COEUR));
        royalFlush.add(new Card(13, Color.COEUR));
        royalFlush.add(new Card(1, Color.COEUR));

        boolean isRoyalFlushResult = (boolean) isRoyalFlushMethod.invoke(null, royalFlush);

        assertTrue(isRoyalFlushResult, "Une main 10,J,Q,K,A de même couleur devrait être une quinte flush royale");

        List<Card> straightFlush = new ArrayList<>();
        straightFlush.add(new Card(5, Color.PIQUE));
        straightFlush.add(new Card(6, Color.PIQUE));
        straightFlush.add(new Card(7, Color.PIQUE));
        straightFlush.add(new Card(8, Color.PIQUE));
        straightFlush.add(new Card(9, Color.PIQUE));

        boolean isStraightFlushNotRoyal = (boolean) isRoyalFlushMethod.invoke(null, straightFlush);

        assertFalse(isStraightFlushNotRoyal, "Une quinte flush 5,6,7,8,9 ne devrait pas être une quinte flush royale");

        List<Card> rightCardsWrongColors = new ArrayList<>();
        rightCardsWrongColors.add(new Card(10, Color.COEUR));
        rightCardsWrongColors.add(new Card(11, Color.PIQUE));
        rightCardsWrongColors.add(new Card(12, Color.CARREAU));
        rightCardsWrongColors.add(new Card(13, Color.TREFLE));
        rightCardsWrongColors.add(new Card(1, Color.COEUR));

        boolean isMixedColorsRoyalFlush = (boolean) isRoyalFlushMethod.invoke(null, rightCardsWrongColors);

        assertFalse(isMixedColorsRoyalFlush, "Une main avec les bonnes cartes mais de couleurs différentes ne devrait pas être une quinte flush royale");
    }

    @Test
    @DisplayName("Test de gestion des cas particuliers dans isSequential")
    public void testIsSequentialSpecialCases() throws Exception {
        java.lang.reflect.Method isSequentialMethod = Main.class.getDeclaredMethod("isSequential", List.class);
        isSequentialMethod.setAccessible(true);

        List<Card> aceStartStraight = new ArrayList<>();
        aceStartStraight.add(new Card(1, Color.COEUR));
        aceStartStraight.add(new Card(2, Color.PIQUE));
        aceStartStraight.add(new Card(3, Color.CARREAU));
        aceStartStraight.add(new Card(4, Color.TREFLE));
        aceStartStraight.add(new Card(5, Color.COEUR));

        boolean isAceStartStraight = (boolean) isSequentialMethod.invoke(null, aceStartStraight);

        assertFalse(isAceStartStraight, "La méthode actuelle ne devrait pas détecter A,2,3,4,5 comme séquentielle car l'As est considéré comme 1");

        List<Card> unorderedStraight = new ArrayList<>();
        unorderedStraight.add(new Card(7, Color.COEUR));
        unorderedStraight.add(new Card(3, Color.PIQUE));
        unorderedStraight.add(new Card(6, Color.CARREAU));
        unorderedStraight.add(new Card(4, Color.TREFLE));
        unorderedStraight.add(new Card(5, Color.COEUR));

        boolean isUnorderedStraight = (boolean) isSequentialMethod.invoke(null, unorderedStraight);

        assertTrue(isUnorderedStraight, "La méthode devrait détecter 3,4,5,6,7 comme séquentielle même si les cartes sont données dans un ordre différent");
    }
}
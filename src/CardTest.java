import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card(10, Color.COEUR);
        assertEquals(10, card.getNumber());
        assertEquals(Color.COEUR, card.getColor());
    }

    @Test
    public void testCardToString() {
        Card as = new Card(1, Color.PIQUE);
        assertEquals("As de PIQUE", as.toString());

        Card sept = new Card(7, Color.CARREAU);
        assertEquals("7 de CARREAU", sept.toString());

        Card valet = new Card(11, Color.TREFLE);
        assertEquals("Valet de TREFLE", valet.toString());

        Card dame = new Card(12, Color.COEUR);
        assertEquals("Dame de COEUR", dame.toString());

        Card roi = new Card(13, Color.PIQUE);
        assertEquals("Roi de PIQUE", roi.toString());
    }

    @Test
    public void testInvalidCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(0, Color.COEUR);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Card(14, Color.COEUR);
        });
    }
}
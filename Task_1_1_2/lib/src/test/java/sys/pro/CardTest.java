package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void flip() {
        Card c = new Card(Suit.Clubs, Rank.Seven);
        assertTrue(c.isFaceUp());
        assertEquals(c.toString(), "7 of clubs");
        c.flip();
        assertFalse(c.isFaceUp());
        assertEquals(c.toString(), "<hidden card>");
    }
}

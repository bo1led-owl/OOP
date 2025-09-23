package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

class DefaultDeckTest {
    @Test
    void exhaustDeck() {
        DefaultDeck d = new DefaultDeck();

        var cards = new HashSet<Card>();
        while (true) {
            Card c = d.nextCard();
            if (c == null) {
                break;
            }
            assertFalse(cards.contains(c));
            cards.add(c);
        }
        assertEquals(cards.size(), 52);
    }
}

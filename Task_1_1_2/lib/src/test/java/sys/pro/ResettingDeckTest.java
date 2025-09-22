package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ResettingDeckTest {
    @Test
    void simple() {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.Spades, Rank.Three));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        cards.add(new Card(Suit.Diamonds, Rank.Queen));
        cards.add(new Card(Suit.Clubs, Rank.Seven));

        ResettingDeck<CustomDeck> rd = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        for (int i = 0; i < 7; ++i) {
            for (Card c : cards) {
                assertEquals(c, rd.nextCard());
            }
        }
        assertNotEquals(rd.nextCard(), null);
    }
}

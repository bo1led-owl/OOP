package sys.pro;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CustomDeckTest {
    @Test
    void simple() {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.Spades, Rank.Three));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        cards.add(new Card(Suit.Diamonds, Rank.Queen));
        cards.add(new Card(Suit.Clubs, Rank.Seven));
        CustomDeck d = new CustomDeck(cards);

        for (Card c : cards) {
            assertEquals(c, d.nextCard());
        }
        assertEquals(d.nextCard(), null);
    }
}

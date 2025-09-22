package sys.pro;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class HandTest {
    @Test
    void simple() {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.Spades, Rank.Three));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        cards.add(new Card(Suit.Diamonds, Rank.Queen));
        cards.add(new Card(Suit.Clubs, Rank.Seven));

        Hand h = new Hand();
        for (Card c : cards) {
            h.addCard(c);
        }

        for (int i = 0; i < cards.size(); ++i) {
            assertEquals(h.get(i), cards.get(i));
        }
    }

    @Test
    void simpleChipsCalculation() {
        Hand h = new Hand();
        h.addCard(new Card(Suit.Diamonds, Rank.Four));
        h.addCard(new Card(Suit.Spades, Rank.Ten));
        assertEquals(h.chips(), 14);
    }

    @Test
    void oneAceChipsCalculation() {
        Hand h = new Hand();
        h.addCard(new Card(Suit.Diamonds, Rank.Four));
        h.addCard(new Card(Suit.Spades, Rank.Ace));
        assertEquals(h.chips(), 15);
        h.addCard(new Card(Suit.Hearts, Rank.Nine));
        assertEquals(h.chips(), 14);
    }

    @Test
    void multipleAcesChipsCalculation() {
        Hand h = new Hand();
        h.addCard(new Card(Suit.Diamonds, Rank.Four));
        h.addCard(new Card(Suit.Spades, Rank.Ace));
        h.addCard(new Card(Suit.Hearts, Rank.Ace));
        h.addCard(new Card(Suit.Clubs, Rank.Ace));
        assertEquals(h.chips(), 17);
    }

    @Test
    void containsBlackjack() {
        Hand h1 = new Hand();
        h1.addCard(new Card(Suit.Diamonds, Rank.Four));
        h1.addCard(new Card(Suit.Spades, Rank.Queen));
        assertFalse(h1.containsBlackjack());

        Hand h2 = new Hand();
        h2.addCard(new Card(Suit.Diamonds, Rank.Ace));
        h2.addCard(new Card(Suit.Spades, Rank.Queen));
        assertTrue(h2.containsBlackjack());
    }
}

package sys.pro;

import java.util.ArrayList;

/** Deck with customizable list of cards. */
public class CustomDeck implements Deck {
    private ArrayList<Card> cards;
    private int idx;

    public CustomDeck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /** Get next card. */
    public Card nextCard() {
        if (idx >= cards.size()) {
            return null;
        }

        return cards.get(idx++);
    }
}

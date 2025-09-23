package sys.pro;

import java.util.function.Supplier;

/** Deck that takes cards from decks given by the passed supplier. */
public class ResettingDeck<D extends Deck> implements Deck {
    private Supplier<D> supplier;
    private D deck;

    /** Create a new repeating deck. */
    public ResettingDeck(Supplier<D> supplier) {
        this.supplier = supplier;
        deck = this.supplier.get();
    }

    /** Get next card, reset if the underlying deck returns `null`. */
    public Card nextCard() {
        Card res = deck.nextCard();
        if (res == null) {
            deck = supplier.get();
            res = deck.nextCard();
        }

        return res;
    }
}

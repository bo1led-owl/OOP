package sys.pro;

import java.util.function.Supplier;

public class ResettingDeck<D extends Deck> implements Deck {
    private Supplier<D> supplier;
    private D deck;

    public ResettingDeck(Supplier<D> supplier) {
        this.supplier = supplier;
        deck = this.supplier.get();
    }

    public Card nextCard() {
        Card res = deck.nextCard();
        if (res == null) {
            deck = supplier.get();
            res = deck.nextCard();
        }

        return res;
    }
}

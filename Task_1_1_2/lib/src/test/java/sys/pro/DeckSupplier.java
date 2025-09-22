package sys.pro;

import java.util.ArrayList;
import java.util.function.Supplier;

public class DeckSupplier implements Supplier<CustomDeck> {
    private ArrayList<Card> cards;

    public DeckSupplier(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public CustomDeck get() {
        return new CustomDeck(cards);
    }
}

package sys.pro;

import java.util.function.Supplier;
import java.util.ArrayList;

public class DeckSupplier implements Supplier<CustomDeck> {
    private ArrayList<Card> cards;
    
    public DeckSupplier(ArrayList<Card> cards) {
        this.cards = cards;
    }
    
    public CustomDeck get() {
        return new CustomDeck(cards);
    }
}


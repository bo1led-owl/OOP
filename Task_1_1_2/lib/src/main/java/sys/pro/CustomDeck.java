package sys.pro;

import java.util.ArrayList;
import java.util.Collections;

public class CustomDeck implements Deck {
  private ArrayList<Card> cards;
  private int i;

  public CustomDeck(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public Card nextCard() {
    if (i >= cards.size()) {
      return null;
    }

    return cards.get(i++);
  }
}

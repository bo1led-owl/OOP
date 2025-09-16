package sys.pro;

public class ResettingDeck {
  private Deck deck;

  public ResettingDeck() {
    deck = new Deck();
  }

  public Card nextCard() {
    Card res = deck.nextCard();
    if (res == null) {
      deck = new Deck();
    }

    return deck.nextCard();
  }
}

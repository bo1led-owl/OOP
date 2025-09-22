package sys.pro;

import sys.pro.Card;

/** A deck of cards. */
public interface Deck {
  /** Extract a card from the deck. */
  public Card nextCard();
}

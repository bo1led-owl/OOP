package sys.pro;

import java.util.ArrayList;

/** Player's hand, a collection of cards. */
public class Hand {
  private static final int SCORE_LIMIT = 21;
  
  private ArrayList<Card> cards;

  public Hand() {
    cards = new ArrayList<>();
  }

  /** Add a card to hand. */
  public void addCard(Card card) {
    cards.add(card);
  }

  /** Get i'th card from the hand. */
  public Card get(int i) {
    return cards.get(i);
  }

  /** Calculate the chips of the combination held. */
  public int chips() {
    int scoreWithoutAces = cards
        .stream()
        .filter(c -> !c.rank().isAce() && c.isFaceUp())
        .map(c -> c.chips())
        .reduce(0, Integer::sum);

    int amountOfAces = (int) cards
        .stream()
        .filter(c -> c.rank().isAce() && c.isFaceUp())
        .count();

    for (int lowAces = 0; lowAces <= amountOfAces; ++lowAces) {
      int highAces = amountOfAces - lowAces;
      int score = scoreWithoutAces + highAces * 11 + lowAces;
      if (score <= SCORE_LIMIT) {
        return score;
      }
    }

    return scoreWithoutAces + amountOfAces;
  }

  /** Check whether the hand contains a blackjack. */
  public boolean containsBlackjack() {
    return cards.stream().anyMatch(c -> c.chips() == 11)
        && cards.stream().anyMatch(c -> c.chips() == 10);
  }

  @Override
  public String toString() {
    String chips = Integer.toString(chips());

    if (cards.stream().anyMatch(c -> !c.isFaceUp())) {
      chips += " + ?";
    }
    return cards.toString() + " = " + chips;
  }
}

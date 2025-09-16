package sys.pro;

import java.util.ArrayList;

public class Hand {
  private ArrayList<Card> cards;

  public Hand() {
    cards = new ArrayList<>();
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public Card get(int i) {
    return cards.get(i);
  }

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
      if (score <= 21) {
        return score;
      }
    }

    return scoreWithoutAces + amountOfAces;
  }

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

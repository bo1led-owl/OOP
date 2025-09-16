package sys.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import sys.pro.ranks.Ace;
import sys.pro.ranks.Jack;
import sys.pro.ranks.King;
import sys.pro.ranks.Number;
import sys.pro.ranks.Queen;

public class Deck {
  private ArrayList<Card> cards;
  private Random random;

  private static Stream<Card> cardsWithRank(Rank r) {
    final Suit[] suits = new Suit[] { Suit.Spades, Suit.Hearts, Suit.Clubs, Suit.Diamonds };
    return Arrays.stream(suits).map(s -> new Card(s, r));
  }

  public Deck() {
    cards = new ArrayList<>();

    for (int i = 2; i <= 10; ++i) {
      cardsWithRank(new Number(i)).forEach(c -> cards.add(c));
    }
    cardsWithRank(new Jack()).forEach(c -> cards.add(c));
    cardsWithRank(new Queen()).forEach(c -> cards.add(c));
    cardsWithRank(new King()).forEach(c -> cards.add(c));
    cardsWithRank(new Ace()).forEach(c -> cards.add(c));

    random = new Random(System.currentTimeMillis());
  }

  public Card nextCard() {
    if (cards.isEmpty()) {
      return null;
    }

    int i = random.nextInt(cards.size());
    return cards.remove(i);
  }
}
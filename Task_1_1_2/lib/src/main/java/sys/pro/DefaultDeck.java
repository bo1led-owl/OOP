package sys.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/** Default, 52 cards, randomized deck. */
public class DefaultDeck implements Deck {
    private ArrayList<Card> cards;
    private Random random;

    private static Stream<Card> cardsWithRank(Rank r) {
        final Suit[] suits = Suit.values();
        return Arrays.stream(suits).map(s -> new Card(s, r));
    }

    /** Create a new deck. */
    public DefaultDeck() {
        this(new Random(System.currentTimeMillis()));
    }

    /** Create a new deck with provided `Random`. */
    public DefaultDeck(Random random) {
        cards = new ArrayList<>();

        for (Rank rank : Rank.values()) {
            cardsWithRank(rank).forEach(c -> cards.add(c));
        }

        this.random = random;
    }

    /** Get next random card from the remaining cards in the deck or `null` if deck is empty. */
    public Card nextCard() {
        if (cards.isEmpty()) {
            return null;
        }

        int i = random.nextInt(cards.size());
        return cards.remove(i);
    }
}

package sys.pro;

/** Playing card. */
public class Card {
    private Suit suit;
    private Rank rank;
    private boolean faceUp;

    /** Create a new card. */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        faceUp = true;
    }

    /** Check whether the card is facing up. */
    public boolean isFaceUp() {
        return faceUp;
    }

    /** Flip the card. */
    public Card flip() {
        faceUp = !faceUp;
        return this;
    }

    /** Get card's suit. */
    public Suit suit() {
        return suit;
    }

    /** Get card's rank. */
    public Rank rank() {
        return rank;
    }

    /** Get chips for the card. */
    public int chips() {
        return rank.chips;
    }

    @Override
    public String toString() {
        if (faceUp) {
            return rank + " of " + suit;
        }

        return "<hidden card>";
    }
}

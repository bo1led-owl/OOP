package sys.pro;

public class Card {
    private Suit suit;
    private Rank rank;
    private boolean faceUp;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        faceUp = true;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public Card flip() {
        faceUp = !faceUp;
        return this;
    }

    public Suit suit() {
      return suit;
    }

    public Rank rank() {
      return rank;
    }

    public int chips() {
      return rank.chips();
    }

    @Override
    public String toString() {
        if (faceUp) {
            return rank + " of " + suit;
        }

        return "<hidden card>";
    }
}

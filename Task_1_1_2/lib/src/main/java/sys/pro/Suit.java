package sys.pro;

/** Card's suit. */
public enum Suit {
    Spades("spades"),
    Hearts("hearts"),
    Clubs("clubs"),
    Diamonds("diamonds");

    public final String name;

    private Suit(String name) {
        this.name = name;
    }

    @Override
    public String toString() throws NullPointerException {
        return this.name;
    }
}

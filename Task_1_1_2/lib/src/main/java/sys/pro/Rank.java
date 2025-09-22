package sys.pro;

/** Card's rank. */
public enum Rank {
    Two(2, "2"),
    Three(3, "3"),
    Four(4, "4"),
    Five(5, "5"),
    Six(6, "6"),
    Seven(7, "7"),
    Eight(8, "8"),
    Nine(9, "9"),
    Ten(10, "10"),
    Jack(10, "Jack"),
    Queen(10, "Queen"),
    King(10, "Kind"),
    Ace(11, "Ace");

    public final int chips;
    public final String name;

    private Rank(int chips, String name) {
        this.chips = chips;
        this.name = name;
    }

    /** Check whether given rank is ace. */
    public boolean isAce() {
        return this.equals(Rank.valueOf("Ace"));
    }

    @Override
    public String toString() {
        return name;
    }
}

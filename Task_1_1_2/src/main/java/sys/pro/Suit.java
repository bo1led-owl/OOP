package sys.pro;

public enum Suit {
    Spades,
    Hearts,
    Clubs,
    Diamonds,
    ;

    @Override
    public String toString() throws NullPointerException {
        switch (this) {
            case Spades:
                return "spades";
            case Hearts:
                return "hearts";
            case Clubs:
                return "clubs";
            case Diamonds:
                return "diamonds";
            case null:
                throw new NullPointerException();
        }
    }
}

package sys.pro.ranks;

import sys.pro.Rank;

public class Ace implements Rank {
    public Ace() {}

    @Override
    public int chips() {
        return 11;
    }

    @Override
    public String toString() {
        return "Ace";
    }

    @Override
    public boolean isAce() {
        return true;
    }
}

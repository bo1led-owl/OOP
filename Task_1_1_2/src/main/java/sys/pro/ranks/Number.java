package sys.pro.ranks;

import sys.pro.Rank;

public class Number implements Rank {
    private int value;

    public Number(int rank) throws IllegalArgumentException {
        if (rank < 2 || rank > 10) {
            throw new IllegalArgumentException("rank is out of bounds");
        }

        value = rank;
    }

    @Override
    public int chips() {
      return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

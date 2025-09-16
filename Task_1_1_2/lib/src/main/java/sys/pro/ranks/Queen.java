package sys.pro.ranks;

import sys.pro.Rank;

public class Queen implements Rank {
    public Queen() {}

    @Override
    public int chips() {
      return 10;
    }

    @Override
    public String toString() {
        return "Queen";
    }
}

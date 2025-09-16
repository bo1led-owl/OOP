package sys.pro.ranks;

import sys.pro.Rank;

public class Jack implements Rank {
    public Jack() {}

    @Override
    public int chips() {
      return 10;
    }

    @Override
    public String toString() {
        return "Jack";
    }
}

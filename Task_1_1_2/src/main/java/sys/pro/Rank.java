package sys.pro;

public interface Rank {
    public int chips();

    default public boolean isAce() {
        return false;
    }
}

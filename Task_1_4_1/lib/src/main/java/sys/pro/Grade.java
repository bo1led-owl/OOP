package sys.pro;

public enum Grade {
    C(3),
    B(4),
    A(5);

    public final int value;

    Grade(int value) {
        this.value = value;
    }
}

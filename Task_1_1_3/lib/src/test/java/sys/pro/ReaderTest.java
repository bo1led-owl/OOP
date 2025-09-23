package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReaderTest {
    @Test
    void simple() {
        var r = new Reader<Integer, Integer>(x -> x).<Integer>map(x -> x + 2);
        assertEquals(44, r.run(42));
    }

    @Test
    void applicative() {
        var r1 = new Reader<Integer, Integer>(x -> x + 2);
        var r2 = Reader.<Integer, Integer>pure(4);

        assertEquals(r1.map(Integer::sum).ap(r2).run(3), 9);
    }
}

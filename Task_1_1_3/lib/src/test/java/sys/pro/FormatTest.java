package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class FormatTest {
    @Test
    void number() {
        assertEquals("4", new Number(4).toString());
    }

    @Test
    void variable() {
        assertEquals("x", new Variable("x").toString());
        assertEquals("y", new Variable("y").toString());
    }

    @Test
    void add() {
        var e = new Add(new Variable("x"), new Add(new Variable("y"), new Number(5)));
        assertEquals("(x + (y + 5))", e.toString());
    }

    @Test
    void sub() {
        var e = new Sub(new Variable("z"), new Sub(new Variable("y"), new Number(3)));
        assertEquals("(z - (y - 3))", e.toString());
    }

    @Test
    void mul() {
        var e = new Mul(new Variable("y"), new Number(3));
        assertEquals("(y * 3)", e.toString());
    }

    @Test
    void div() {
        var e = new Div(new Number(24), new Add(new Number(1), new Number(1)));
        assertEquals("(24 / (1 + 1))", e.toString());
    }
}

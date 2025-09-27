package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SimplificationTest {
    @Test
    void number() {
        assertEquals(new Number(4), new Number(4).simplify());
    }

    @Test
    void variable() {
        assertEquals(new Variable("x"), new Variable("x").simplify());
        assertEquals(new Variable("y"), new Variable("y").simplify());
    }

    @Test
    void add() {
        var e = new Add(new Variable("x"), new Number(0));
        assertEquals(new Variable("x"), e.simplify());

        e = new Add(new Number(0), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());

        e = new Add(new Number(6), new Number(14));
        assertEquals(new Number(20), e.simplify());
    }

    @Test
    void sub() {
        var e = new Sub(new Variable("z"), new Variable("z"));
        assertEquals(new Number(0), e.simplify());

        e = new Sub(new Variable("z"), new Number(0));
        assertEquals(new Variable("z"), e.simplify());

        e = new Sub(new Number(42), new Number(6));
        assertEquals(new Number(36), e.simplify());
    }

    @Test
    void mul() {
        var e = new Mul(new Variable("y"), new Number(1));
        assertEquals(new Variable("y"), e.simplify());

        e = new Mul(new Number(1), new Variable("y"));
        assertEquals(new Variable("y"), e.simplify());

        e = new Mul(new Number(0), new Variable("y"));
        assertEquals(new Number(0), e.simplify());

        e = new Mul(new Variable("y"), new Number(0));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void div() {
        var e = new Div(new Number(12), new Mul(new Number(2), new Number(3)));
        assertEquals(new Number(2), e.simplify());
    }
}

package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DerivativeTest {
    @Test
    void number() {
        assertEquals(new Number(0), new Number(4).derivative("x"));
        assertEquals(new Number(0), new Number(6).derivative("y"));
        assertEquals(new Number(0), new Number(2).derivative("z"));
    }

    @Test
    void variable() {
        var v = new Variable("x");
        assertEquals(new Number(1), v.derivative("x"));
        assertEquals(new Number(0), v.derivative("y"));
        assertEquals(new Number(0), v.derivative("z"));
    }

    @Test
    void add() {
        var v = new Add(new Variable("x"), new Number(42));
        assertEquals(new Add(new Number(1), new Number(0)), v.derivative("x"));
        assertEquals(new Add(new Number(0), new Number(0)), v.derivative("y"));
    }

    @Test
    void sub() {
        var v = new Sub(new Number(14), new Variable("x"));
        assertEquals(new Sub(new Number(0), new Number(1)), v.derivative("x"));
        assertEquals(new Sub(new Number(0), new Number(0)), v.derivative("y"));
    }

    @Test
    void mul() {
        var v = new Mul(new Number(4), new Variable("x"));
        assertEquals(
                new Add(
                        new Mul(new Number(0), new Variable("x")),
                        new Mul(new Number(4), new Number(1))),
                v.derivative("x"));
    }

    @Test
    void div() {
        var v = new Div(new Number(4), new Variable("x"));
        assertEquals(
                new Div(
                        new Sub(
                                new Mul(new Number(0), new Variable("x")),
                                new Mul(new Number(4), new Number(1))),
                        new Mul(new Variable("x"), new Variable("x"))),
                v.derivative("x"));
    }
}

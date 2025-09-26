package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void number() {
        assertEquals(new Number(1), Parser.parse("1"));
        assertEquals(new Number(42), Parser.parse("42"));
        assertEquals(new Number(1024), Parser.parse("1024"));
        assertEquals(new Number(1024), Parser.parse("(1024)"));
    }

    @Test
    void variable() {
        assertEquals(new Variable("x"), Parser.parse("(x)"));
        assertEquals(new Variable("foo"), Parser.parse("foo"));
    }

    @Test
    void add() {
        assertEquals(new Add(new Number(42), new Variable("x")), Parser.parse("42 + x"));
        assertEquals(new Add(new Number(42), new Variable("x")), Parser.parse("42+x"));
    }

    @Test
    void sub() {
        assertEquals(new Sub(new Number(42), new Variable("x")), Parser.parse("42 - x"));
        assertEquals(new Sub(new Number(42), new Variable("x")), Parser.parse("42-x"));
    }

    @Test
    void mul() {
        assertEquals(new Mul(new Number(42), new Variable("x")), Parser.parse("42 * x"));
        assertEquals(new Mul(new Number(42), new Variable("x")), Parser.parse("42*x"));
    }

    @Test
    void div() {
        assertEquals(new Div(new Number(42), new Variable("x")), Parser.parse("42 / x"));
        assertEquals(new Div(new Number(42), new Variable("x")), Parser.parse("42/x"));
    }

    @Test
    void combinations() {
        assertEquals(
                new Add(new Mul(new Number(42), new Number(3)), new Variable("x")),
                Parser.parse("42 * 3 + x"));
        assertEquals(
                new Mul(new Number(42), new Sub(new Number(3), new Variable("x"))),
                Parser.parse("42 * (3 - x)"));
    }
}

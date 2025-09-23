package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ParserTest {
    private static Expression parse(String input) {
        return (new Parser(input)).parse();
    }

    @Test
    void number() {
        assertEquals(new Number(1), parse("1"));
        assertEquals(new Number(42), parse("42"));
        assertEquals(new Number(1024), parse("1024"));
        assertEquals(new Number(1024), parse("(1024)"));
    }

    @Test
    void variable() {
        assertEquals(new Variable("x"), parse("(x)"));
        assertEquals(new Variable("foo"), parse("foo"));
    }

    @Test
    void add() {
        assertEquals(new Add(new Number(42), new Variable("x")), parse("42 + x"));
        assertEquals(new Add(new Number(42), new Variable("x")), parse("42+x"));
    }

    @Test
    void sub() {
        assertEquals(new Sub(new Number(42), new Variable("x")), parse("42 - x"));
        assertEquals(new Sub(new Number(42), new Variable("x")), parse("42-x"));
    }

    @Test
    void mul() {
        assertEquals(new Mul(new Number(42), new Variable("x")), parse("42 * x"));
        assertEquals(new Mul(new Number(42), new Variable("x")), parse("42*x"));
    }

    @Test
    void div() {
        assertEquals(new Div(new Number(42), new Variable("x")), parse("42 / x"));
        assertEquals(new Div(new Number(42), new Variable("x")), parse("42/x"));
    }

    @Test
    void combinations() {
        assertEquals(
                new Add(new Mul(new Number(42), new Number(3)), new Variable("x")),
                parse("42 * 3 + x"));
        assertEquals(
                new Mul(new Number(42), new Sub(new Number(3), new Variable("x"))),
                parse("42 * (3 - x)"));
    }
}

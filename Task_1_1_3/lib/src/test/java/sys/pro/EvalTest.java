package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EvalTest {
    private static final String testCtx = "x = 10; y = 13; z = 42";

    @Test
    void number() {
        assertEquals(4, new Number(4).eval(testCtx));
    }

    @Test
    void variable() {
        assertEquals(10, new Variable("x").eval(testCtx));
        assertEquals(13, new Variable("y").eval(testCtx));
        assertEquals(42, new Variable("z").eval(testCtx));
    }

    @Test
    void add() {
        var e = new Add(new Variable("x"), new Add(new Variable("y"), new Number(5)));
        assertEquals(28, e.eval(testCtx));
    }

    @Test
    void sub() {
        var e = new Sub(new Variable("z"), new Sub(new Variable("y"), new Number(3)));
        assertEquals(32, e.eval(testCtx));
    }

    @Test
    void mul() {
        var e = new Mul(new Variable("y"), new Number(3));
        assertEquals(39, e.eval(testCtx));
    }

    @Test
    void div() {
        var e = new Div(new Number(24), new Add(new Number(1), new Number(1)));
        assertEquals(12, e.eval(testCtx));
    }
}

package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class EvalTest {
    private static HashMap<String, Integer> testCtx() {
        return Expression.parseCtx("x = 10; y = 13; z = 42");
    }

    @Test
    void number() {
        assertEquals(4, new Number(4).eval().run(testCtx()));
    }

    @Test
    void variable() {
        assertEquals(10, new Variable("x").eval().run(testCtx()));
        assertEquals(13, new Variable("y").eval().run(testCtx()));
        assertEquals(42, new Variable("z").eval().run(testCtx()));
    }

    @Test
    void add() {
        var e = new Add(new Variable("x"), new Add(new Variable("y"), new Number(5)));
        assertEquals(28, e.eval().run(testCtx()));
    }

    @Test
    void sub() {
        var e = new Sub(new Variable("z"), new Sub(new Variable("y"), new Number(3)));
        assertEquals(32, e.eval().run(testCtx()));
    }

    @Test
    void mul() {
        var e = new Mul(new Variable("y"), new Number(3));
        assertEquals(39, e.eval().run(testCtx()));
    }

    @Test
    void div() {
        var e = new Div(new Number(24), new Add(new Number(1), new Number(1)));
        assertEquals(12, e.eval().run(testCtx()));
    }
}

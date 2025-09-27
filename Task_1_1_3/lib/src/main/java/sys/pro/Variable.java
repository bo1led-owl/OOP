package sys.pro;

import java.util.HashMap;

/** Variable. */
public class Variable implements Expression {
    private final String name;

    /** Create new variable. */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Reader<HashMap<String, Integer>, Integer> eval() {
        return new Reader<HashMap<String, Integer>, Integer>(ctx -> ctx.get(name));
    }

    @Override
    public Expression derivative(String variable) {
        return (name.equals(variable)) ? new Number(1) : new Number(0);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Variable && name.equals(((Variable) that).name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public boolean containsVariables() {
        return true;
    }
}

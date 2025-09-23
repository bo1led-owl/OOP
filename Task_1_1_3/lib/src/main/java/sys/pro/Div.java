package sys.pro;

import java.util.HashMap;

/** Division. */
public class Div implements Expression {
  private final Expression lhs;
  private final Expression rhs;
  
  /** Create new division. */
  public Div(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  private static Integer div(Integer x, Integer y) {
    return x / y;
  }

  @Override
  public Reader<HashMap<String, Integer>, Integer> eval() {
    return lhs.eval().map(Div::div).ap(rhs.eval());
  }

  @Override
  public Expression derivative(String variable) {
    var u = lhs;
    var v = rhs;
    var uPrime = u.derivative(variable);
    var vPrime = v.derivative(variable);
    return new Div(new Sub(new Mul(uPrime, v), new Mul(u, vPrime)), new Mul(v, v));
  }

  @Override
  public boolean equals(Object that) {
    return that instanceof Div && lhs.equals(((Div)that).lhs) && rhs.equals(((Div)that).rhs);
  }

  @Override
  public String toString() {
    return "(" + lhs + " / " + rhs + ")";
  }

  @Override
  public Expression simplify() {
    var newLhs = lhs.simplify();
    var newRhs = rhs.simplify();

    Expression res = new Div(newLhs, newRhs);
    if (!res.containsVariables()) {
      res = new Number(res.eval().run(new HashMap<>()));
    }

    return res;
  }

  @Override
  public boolean containsVariables() {
    return lhs.containsVariables() || rhs.containsVariables();
  }
}

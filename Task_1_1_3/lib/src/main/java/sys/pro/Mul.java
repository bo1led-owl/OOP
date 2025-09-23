package sys.pro;

import java.util.HashMap;

/** Multiplication. */
public class Mul implements Expression {
  private final Expression lhs;
  private final Expression rhs;
  
  /** Create new multiplication. */
  public Mul(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  private static Integer mul(Integer x, Integer y) {
    return x * y;
  }

  @Override
  public Reader<HashMap<String, Integer>, Integer> eval() {
    return lhs.eval().map(Mul::mul).ap(rhs.eval());
  }

  @Override
  public Expression derivative(String variable) {
    var u = lhs;
    var v = rhs;
    var uPrime = u.derivative(variable);
    var vPrime = v.derivative(variable);
    return new Add(new Mul(uPrime, v), new Mul(u, vPrime));
  }

  @Override
  public boolean equals(Object that) {
    return that instanceof Mul && lhs.equals(((Mul)that).lhs) && rhs.equals(((Mul)that).rhs);
  }

  @Override
  public String toString() {
    return "(" + lhs + " * " + rhs + ")";
  }

  @Override
  public Expression simplify() {
    var newLhs = lhs.simplify();
    var newRhs = rhs.simplify();

    Expression res = null;
    if (newLhs instanceof Number && ((Number)newLhs).value == 0
     || newRhs instanceof Number && ((Number)newRhs).value == 0) {
      res = new Number(0);
    } else if (newLhs instanceof Number && ((Number)newLhs).value == 1) {
      res = newRhs;
    } else if (newRhs instanceof Number && ((Number)newRhs).value == 1) {
      res = newLhs;
    } else {
      res = new Mul(newLhs, newRhs);
    }

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

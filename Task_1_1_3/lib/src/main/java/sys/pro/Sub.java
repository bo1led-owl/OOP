package sys.pro;

import java.util.HashMap;

/** Subtraction. */
public class Sub implements Expression {
  private final Expression lhs;
  private final Expression rhs;
  
  /** Create new subtraction. */
  public Sub(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  private static Integer sub(Integer x, Integer y) {
    return x - y;
  }

  @Override
  public Reader<HashMap<String, Integer>, Integer> eval() {
    return lhs.eval().map(Sub::sub).ap(rhs.eval());
  }

  @Override
  public Expression derivative(String variable) {
    return new Sub(lhs.derivative(variable), rhs.derivative(variable));
  }

  @Override
  public boolean equals(Object that) {
    return that instanceof Sub && lhs.equals(((Sub)that).lhs) && rhs.equals(((Sub)that).rhs);
  }

  @Override
  public String toString() {
    return "(" + lhs + " - " + rhs + ")";
  }

  @Override
  public Expression simplify() {
    var newLhs = lhs.simplify();
    var newRhs = rhs.simplify();

    Expression res = null;
    if (newRhs instanceof Number && ((Number)newRhs).value == 0) {
      res = newLhs;
    } else if (newLhs.equals(newRhs)) {
      res = new Number(0);
    } else {
      res = new Sub(newLhs, newRhs);
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

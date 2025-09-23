package sys.pro;

import java.util.HashMap;

/** Addition. */
public class Add implements Expression {
  private final Expression lhs;
  private final Expression rhs;
  
  /** Create new addition. */
  public Add(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public Reader<HashMap<String, Integer>, Integer> eval() {
    return lhs.eval().map(Integer::sum).ap(rhs.eval());
  }

  @Override
  public Expression derivative(String variable) {
    return new Add(lhs.derivative(variable), rhs.derivative(variable));
  }

  @Override
  public boolean equals(Object that) {
    return that instanceof Add && lhs.equals(((Add)that).lhs) && rhs.equals(((Add)that).rhs);
  }

  @Override
  public String toString() {
    return "(" + lhs + " + " + rhs + ")";
  }

  @Override
  public Expression simplify() {
    var newLhs = lhs.simplify();
    var newRhs = rhs.simplify();

    Expression res = null;
    if (newLhs instanceof Number && ((Number)newLhs).value == 0) {
      res = newRhs;
    } else if (newRhs instanceof Number && ((Number)newRhs).value == 0) {
      res = newLhs;
    } else {
      res = new Add(newLhs, newRhs);
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

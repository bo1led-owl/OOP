package sys.pro;

import java.util.HashMap;

/** Integer immediate. */
public class Number implements Expression {
  public final Integer value;
  
  /** Create new immediate. */
  public Number(Integer value) {
    this.value = value;
  }

  @Override
  public Reader<HashMap<String, Integer>, Integer> eval() {
    return Reader.pure(value);
  }

  @Override
  public Expression derivative(String variable) {
    return new Number(0);
  }

  @Override
  public boolean equals(Object that) {
    return that instanceof Number && value.equals(((Number)that).value);
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public Expression simplify() {
    return this;
  }

  @Override
  public boolean containsVariables() {
    return false;
  }
}

package sys.pro;

import java.util.HashMap;

/** Expression interface. */
public interface Expression {
  /** Parse context from string. */
  public static HashMap<String, Integer> parseCtx(String vars) {
    String[] parts = vars.split("\\s*;\\s*");
    var parsedVars = new HashMap<String, Integer>();
    for (String part : parts) {
      String[] def = part.split("\\s*=\\s*");
      parsedVars.put(def[0], Integer.valueOf(def[1]));
    }
    return parsedVars;
  }

  /** Evaluate this expression. */
  public Reader<HashMap<String, Integer>, Integer> eval();
  /** Calculate the derivative of this expression. */
  public Expression derivative(String variable);
  /** Simplify this expression. */
  public Expression simplify();
  /** Check whether this expression or any of its children has variables in it. */
  public boolean containsVariables();
}

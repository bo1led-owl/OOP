package sys.pro;

import java.util.ArrayList;
import java.util.Iterator;

/** Expression parser. */
public class Parser {
    private Iterator<String> tokens;
    private String curToken;

    private ArrayList<String> tokenize(String input) {
        var res = new ArrayList<String>();

        for (int i = 0; i < input.length(); ) {
            if (Character.isWhitespace(input.charAt(i))) {
                ++i;
                continue;
            }

            if (input.charAt(i) == '+'
                    || input.charAt(i) == '-'
                    || input.charAt(i) == '*'
                    || input.charAt(i) == '/'
                    || input.charAt(i) == '('
                    || input.charAt(i) == ')') {
                res.add(Character.toString(input.charAt(i)));
                ++i;
                continue;
            }

            String tok = "";
            if (Character.isAlphabetic(input.charAt(i))) {
                for (; i < input.length() && Character.isAlphabetic(input.charAt(i)); ++i) {
                    tok += input.charAt(i);
                }
                res.add(tok);
                continue;
            }
            if (Character.isDigit(input.charAt(i))) {
                for (; i < input.length() && Character.isDigit(input.charAt(i)); ++i) {
                    tok += input.charAt(i);
                }
                res.add(tok);
                continue;
            }
        }

        return res;
    }

    /** Create new parser from given string. */
    public Parser(String input) {
        tokens = tokenize(input).iterator();
        advance();
    }

    /** Perform parsing. */
    public Expression parse() {
        return parseBin(0, parseTerm());
    }

    private void advance() {
        curToken = (tokens.hasNext() ? tokens.next() : null);
    }

    private Expression parseTerm() {
        var tok = curToken;
        if (tok.equals("(")) {
            advance(); // eat '('
            var res = parse();
            advance(); // eat ')'
            return res;
        }
        if (Character.isAlphabetic(tok.charAt(0))) {
            advance();
            return new Variable(tok);
        }
        if (Character.isDigit(tok.charAt(0))) {
            advance();
            return new Number(Integer.valueOf(tok));
        }

        return null;
    }

    private int curBinopPrecedence() {
        if (curToken == null) {
            return -1;
        }
        if (curToken.equals("+") || curToken.equals("-")) {
            return 1;
        }
        if (curToken.equals("*") || curToken.equals("/")) {
            return 2;
        }
        return -1;
    }

    private Expression parseBin(int opPrec, Expression lhs) {
        while (true) {
            if (curToken == null) {
                return lhs;
            }

            int curPrec = curBinopPrecedence();
            if (curPrec < opPrec) {
                return lhs;
            }
            var binOp = curToken;
            advance();

            var rhs = parseTerm();
            int nextPrec = curBinopPrecedence();
            if (curPrec < nextPrec) {
                rhs = parseBin(curPrec + 1, rhs);
            }

            if (binOp.equals("+")) {
                lhs = new Add(lhs, rhs);
            } else if (binOp.equals("-")) {
                lhs = new Sub(lhs, rhs);
            } else if (binOp.equals("*")) {
                lhs = new Mul(lhs, rhs);
            } else if (binOp.equals("/")) {
                lhs = new Div(lhs, rhs);
            }
        }
    }
}

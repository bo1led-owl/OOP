package sys.pro;

import java.util.function.Function;
import java.util.function.BiFunction;

/** Reader monad. */
public class Reader<R, A> {
  private final Function<? super R, ? extends A> fn;

  /** Create new `Reader`. */
  public Reader(Function<? super R, ? extends A> f) {
    fn = f;
  }

  private Reader(A x) {
    fn = (_ -> x);
  }

  /** Create `Reader` with constant expression. */
  public static <R, A> Reader<R, A> pure(A x) {
    return new Reader<R, A>(x);
  }
  
  /** Run `Reader` in with given context. */
  public A run(R ctx) {
    return fn.apply(ctx);
  }

  /** Map computation in `Reader`. */
  public <B> Reader<R, B> map(Function<? super A, ? extends B> f) {
    return new Reader<R, B>(fn.andThen(f));
  }

  /** Map computation in `Reader` with partial application of a binary function. */
  public <B, C> ApplicativeReader<R, B, C> map(BiFunction<? super A, ? super B, ? extends C> f) {
    return new ApplicativeReader<R, B, C>(ctx -> x -> f.apply(run(ctx), x));
  }
}

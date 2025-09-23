package sys.pro;

import java.util.function.Function;
import java.util.function.BiFunction;

/** Reader monad with applicative functor capabilities. */
public class ApplicativeReader<R, A, B> extends Reader<R, Function<? super A, ? extends B>> {
  /** Create new applicative `Reader`. */
  public ApplicativeReader(Function<? super R, Function<? super A, ? extends B>> f) {
    super(f);
  }
  
  /** Apply reader to value obtained by another `Reader`. */
  public Reader<R, B> ap(Reader<? super R, ? extends A> x) {
    return new Reader<R, B>(ctx -> run(ctx).apply(x.run(ctx)));
  }
}

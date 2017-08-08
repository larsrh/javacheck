package javacheck;

import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Gen<T> {

  T generate(int size, SplittableRandom rnd);

  default public Gen<T> filter(Predicate<T> pred) {
    return (size, rnd) -> {
      for (int i = 0; i < 1000; ++i) {
        T t = this.generate(size, rnd);
        if (pred.test(t))
          return t;
      }
      throw new IllegalArgumentException("too many tries");
    };
  }

  default public <U> Gen<U> map(Function<T, U> f) {
    return (size, rnd) -> f.apply(this.generate(size, rnd));
  }

  default public List<T> sample(int maxSize) {
    SplittableRandom rnd = new SplittableRandom();
    return IntStream
      .rangeClosed(1, maxSize)
      .boxed()
      .map(size -> generate(size, rnd))
      .collect(Collectors.toList());
  }

}
package javacheck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Generators {

  private Generators() {}

  public static Gen<Integer> integer() {
    return (size, rnd) -> {
      int range = size * 2 + 1;
      return rnd.nextInt(range) - size;
    };
  }

  public static <T> Gen<List<T>> list(Gen<T> genT) {
    return (size, rnd) -> {
      int length = rnd.nextInt(size + 1);
      return IntStream
        .range(0, length)
        .boxed()
        .map(i -> genT.generate(size, rnd))
        .collect(Collectors.toList());
    };
  }

  public static <T, U> Gen<Pair<T, U>> pair(Gen<T> genT, Gen<U> genU) {
    return (size, rnd) ->
      new Pair<T, U>(genT.generate(size, rnd), genU.generate(size, rnd));
  }

  public static Gen<Rational> rational() {
    return
        pair(integer(), integer())
          .filter(pair -> pair.getSecond() != 0)
          .map(pair -> new Rational(pair.getFirst(), pair.getSecond()));
  }

}
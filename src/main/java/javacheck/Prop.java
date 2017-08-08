package javacheck;

import java.util.LinkedList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Prop {

  public Result run(int size, SplittableRandom rnd);

  public static Prop ofBoolean(boolean result) {
    return (size, rnd) -> Result.ofBoolean(result);
  }

  public static <T> Prop forAll(Gen<T> genT, Function<T, Prop> prop) {
    return (size, rnd) -> {
      T input = genT.generate(size, rnd);
      Prop inner = prop.apply(input);
      Result res = inner.run(size, rnd);
      if (res.isSuccessful()) {
        return Result.successful();
      }
      else {
        List<Object> counterexample = new LinkedList<>();
        counterexample.add(input);
        counterexample.addAll(res.getCounterExample());
        return Result.failed(counterexample);
      }
    };
  }

  public default Result check() {
    SplittableRandom rnd = new SplittableRandom();
    return IntStream
        .rangeClosed(1, 100)
        .boxed()
        .map(size -> run(size, rnd))
        .collect(Collectors.reducing(Result.successful(), Result::merge));
  }

}
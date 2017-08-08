package javacheck;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Result {

  private Optional<List<Object>> counterexample;

  private Result(Optional<List<Object>> counterexample) {
    this.counterexample = counterexample;
  }

  public static Result successful() {
    return new Result(Optional.empty());
  }

  public static Result failed(List<Object> counterexample) {
    return new Result(Optional.of(counterexample));
  }

  public static Result ofBoolean(boolean result) {
    return result ?
      Result.successful() :
      Result.failed(Collections.emptyList());
  }

  public boolean isSuccessful() {
    return !counterexample.isPresent();
  }

  public List<Object> getCounterExample() {
    if (counterexample.isPresent())
      return Collections.unmodifiableList(counterexample.get());

    throw new IllegalStateException("Result is successful");
  }

  public Result merge(Result that) {
    if (this.isSuccessful() && that.isSuccessful())
      return this;
    else if (!this.isSuccessful())
      return this;
    else
      return that;
  }

  @Override
  public String toString() {
    return isSuccessful() ?
      "Result [successful]" :
      ("Result [failed, counterexample = " + counterexample.get().toString() + "]");
  }

}
package javacheck;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    System.out.println(Generators.integer().sample(10));
    System.out.println(Generators.integer().filter(n -> n >= 0).sample(10));
    System.out.println(Generators.list(Generators.integer()).sample(10));
    System.out.println(Generators.rational().sample(10));

    System.out.println(Result.failed(Arrays.asList(1, 2, 3)));

    System.out.println(
      Prop.forAll(Generators.integer(), x ->
        Prop.ofBoolean(x.equals(x))).check()
    );

    System.out.println(
      Prop.forAll(Generators.integer(), x ->
        Prop.forAll(Generators.integer(), y ->
            Prop.ofBoolean(x + y == y + x))).check()
    );

    System.out.println(
        Prop.forAll(Generators.integer(), x ->
          Prop.forAll(Generators.integer(), y ->
              Prop.ofBoolean(x + y + 1 == y + x))).check()
      );
  }

}

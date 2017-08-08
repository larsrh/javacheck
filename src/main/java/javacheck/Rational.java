package javacheck;

public final class Rational {

  private int numerator;
  private int denominator;

  public Rational(int numerator, int denominator) {
    assert denominator != 0;
    this.numerator = numerator;
    this.denominator = denominator;
  }

  public int getNumerator() {
    return numerator;
  }

  public int getDenominator() {
    return denominator;
  }

  @Override
  public String toString() {
    return numerator + "/" + denominator;
  }

}
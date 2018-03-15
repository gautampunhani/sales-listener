package org.jpchase.codingtest.domain;

import java.util.function.Function;

public enum AdjustmentType {
  ADD() {
    @Override
    public Function<Double, Double> getAdjustmentFunction(double value) {
      return initialPrice -> initialPrice + value;
    }
  }, SUBTRACT() {
    @Override
    public Function<Double, Double> getAdjustmentFunction(double value) {
      return initialPrice -> initialPrice - value;
    }
  }, MULTIPLY() {
    @Override
    public Function<Double, Double> getAdjustmentFunction(double value) {
      return initialPrice -> initialPrice * value;
    }
  };

  public Function<Double, Double> getAdjustmentFunction(double value) {
    return Function.identity();
  }
}

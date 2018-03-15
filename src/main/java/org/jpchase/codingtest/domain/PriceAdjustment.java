package org.jpchase.codingtest.domain;

import java.util.function.Function;

public class PriceAdjustment {
  private String productType;
  private double adjustmentValue;
  private AdjustmentType adjustmentType;

  public PriceAdjustment(AdjustmentType adjustmentType, String productType, double adjustmentValue) {
    this.adjustmentType = adjustmentType;
    this.productType = productType;
    this.adjustmentValue = adjustmentValue;
  }

  public String getProductType() {
    return productType;
  }

  public Function<Double, Double> getAdjustmentFunction() {
    return adjustmentType.getAdjustmentFunction(adjustmentValue);
  }

  public String toString() {
    return String.format("%s applied on %s with value %f", adjustmentType.name(), productType, adjustmentValue);
  }
}

package org.jpchase.codingtest.domain;

import java.util.function.Function;

public class Sales{
  private String productType;
  private double price;
  private int quantity;

  public double getPrice() {
    return price;
  }

  public String getProductType() {
    return productType;
  }

  public Sales(String productType, double price, int quantity) {
    this.productType = productType;
    this.price = price;
    this.quantity = quantity;
  }

  public String toString() {
    return String.format("%d of %s sold at %f", quantity, productType, price);
  }

  double getSalesValue() {
    return this.price * this.quantity;
  }

  public void adjust(Function<Double, Double> adjustPrice) {
    this.price = adjustPrice.apply(this.price);
  }
}

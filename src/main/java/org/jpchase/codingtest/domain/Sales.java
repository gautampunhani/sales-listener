package org.jpchase.codingtest.domain;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class Sales {
  private String productType;
  private double price;
  private int quantity;

  public int getQuantity() {
    return quantity;
  }

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
}

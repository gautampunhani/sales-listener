package org.jpchase.codingtest.dto;

import org.jpchase.codingtest.domain.Sales;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesNotification {
  private String productType;
  private Integer quantity;
  private double value;

  public SalesNotification(String productType, double value) {
    this.quantity = 1;
    this.productType = productType;
    this.value = value;
  }

  public SalesNotification(String productType, double value, int quantity) {
    this.quantity = quantity;
    this.productType = productType;
    this.value = value;
  }

  public Sales getSale() {
    return new Sales(productType, value, quantity);
  }
}

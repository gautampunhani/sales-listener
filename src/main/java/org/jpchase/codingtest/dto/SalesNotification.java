package org.jpchase.codingtest.dto;

import org.jpchase.codingtest.domain.AdjustmentType;
import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;

public class SalesNotification {
  private AdjustmentType adjustmentType;

  private NotificationType type;
  private String productType;
  private Integer quantity;
  private double value;
  private double adjustmentValue;

  public SalesNotification(String productType, double value) {
    this.quantity = 1;
    this.productType = productType;
    this.value = value;
    this.type = NotificationType.PRODUCT_PRICE;
  }

  public SalesNotification(AdjustmentType adjustmentType, String productType, double adjustmentValue) {
    this.productType = productType;
    this.type = NotificationType.PRICE_ADJUSTMENT;
    this.adjustmentValue = adjustmentValue;
    this.adjustmentType = adjustmentType;
  }

  public SalesNotification(String productType, double value, int quantity) {
    this.quantity = quantity;
    this.productType = productType;
    this.value = value;
    this.type = NotificationType.PRODUCT_QUANTITY_PRICE;
  }

  public Sales getSale() {
    return new Sales(productType, value, quantity);
  }

  public boolean isSales() {
    return type == NotificationType.PRODUCT_PRICE || type == NotificationType.PRODUCT_QUANTITY_PRICE;
  }

  public PriceAdjustment getPriceAdjustment() {
    return new PriceAdjustment(adjustmentType, productType, adjustmentValue);
  }
}

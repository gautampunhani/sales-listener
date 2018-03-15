package org.jpchase.codingtest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSalesAggregate {
  private Map<String, Double> productSales = new HashMap<>();

  public ProductSalesAggregate(List<Sales> sales) {
    sales.stream().forEach(this::add);
  }

  private void add(Sales sales) {
    this.productSales.put(sales.getProductType(), sales.getSalesValue() + this.productSales.getOrDefault(sales.getProductType(), 0d));
  }

  public String toString() {
    return productSales.toString();
  }
}

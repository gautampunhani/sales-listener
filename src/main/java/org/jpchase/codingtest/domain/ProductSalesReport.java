package org.jpchase.codingtest.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class ProductSalesReport {
  private static Logger logger = Logger.getLogger(ProductSalesReport.class.getName());
  private Map<String, Double> productSales = new HashMap<>();

  public void add(Sales sales) {
    this.productSales.put(sales.getProductType(), sales.getSalesValue() + this.productSales.getOrDefault(sales.getProductType(), 0d));
  }

  public String toString() {
    return productSales.toString();
  }
}

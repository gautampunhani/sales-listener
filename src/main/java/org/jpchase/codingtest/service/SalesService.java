package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;

public interface SalesService {
  void record(Sales sales);
  void adjust(PriceAdjustment adjustment);
  void publishProductSalesReport();
  void publishAdjustmentReport();
}

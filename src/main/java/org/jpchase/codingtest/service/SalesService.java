package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.Sales;

public interface SalesService {
  void record(Sales sales);
  void publishProductSalesReport();
}

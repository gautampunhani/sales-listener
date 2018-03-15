package org.jpchase.codingtest.repository;

import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;

import java.util.List;

public interface SalesRepository {
  void add(Sales sales);
  List<Sales> getSales();
  void recordAdjustmentApplied(PriceAdjustment adjustment);
  List<PriceAdjustment> getAdjustmentsApplied();
}

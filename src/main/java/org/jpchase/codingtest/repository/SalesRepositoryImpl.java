package org.jpchase.codingtest.repository;

import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SalesRepositoryImpl implements SalesRepository {
  private static Logger logger = Logger.getLogger(SalesRepository.class.getName());
  private List<PriceAdjustment> recordedAdjustmentsApplied = new ArrayList<>();
  private List<Sales> recordedSales = new ArrayList<>();

  public void add(Sales sales) {
    logger.info("Recording sales : " + sales);
    recordedSales.add(sales);
  }

  public List<Sales> getSales() {
    return recordedSales;
  }

  @Override
  public void recordAdjustmentApplied(PriceAdjustment adjustment) {
    recordedAdjustmentsApplied.add(adjustment);
  }

  @Override
  public List<PriceAdjustment> getAdjustmentsApplied() {
    return recordedAdjustmentsApplied;
  }
}

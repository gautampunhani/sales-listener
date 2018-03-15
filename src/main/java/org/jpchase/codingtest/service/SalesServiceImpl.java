package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.ProductSalesAggregate;
import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.repository.SalesRepository;

import java.util.logging.Logger;

public class SalesServiceImpl implements SalesService {
  private static Logger logger = Logger.getLogger(SalesServiceImpl.class.getName());
  private SalesRepository salesRepository;

  public SalesServiceImpl(SalesRepository repository) {
    this.salesRepository = repository;
  }

  @Override
  public void record(Sales sales) {
    this.salesRepository.add(sales);
  }

  @Override
  public void adjust(PriceAdjustment priceAdjustment) {
    salesRepository
      .getSales().stream()
      .filter(x -> priceAdjustment.getProductType().equals(x.getProductType()))
      .forEach(x -> x.adjust(priceAdjustment.getAdjustmentFunction()));

    salesRepository.recordAdjustmentApplied(priceAdjustment);
  }

  @Override
  public void publishProductSalesReport() {
    ProductSalesAggregate salesReport = new ProductSalesAggregate(salesRepository.getSales());
    logger.info(salesReport.toString());
  }

  @Override
  public void publishAdjustmentReport() {
    logger.info("Following Adjustments were Applied");
    salesRepository.getAdjustmentsApplied()
      .forEach(adjustment -> logger.info(adjustment.toString()));
  }
}

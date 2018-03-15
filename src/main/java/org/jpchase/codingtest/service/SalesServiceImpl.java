package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.ProductSalesReport;
import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.repository.SalesRepository;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesServiceImpl implements SalesService {
  private static Logger logger = Logger.getLogger(SalesServiceImpl.class.getName());
  private SalesRepository repository;
  private ProductSalesReport salesReport = new ProductSalesReport();

  public SalesServiceImpl(SalesRepository repository) {
    this.repository = repository;
  }

  @Override
  public void record(Sales sales) {
    this.repository.add(sales);
    salesReport.add(sales);
  }

  @Override
  public void publishProductSalesReport() {
    logger.info(salesReport.toString());
  }
}

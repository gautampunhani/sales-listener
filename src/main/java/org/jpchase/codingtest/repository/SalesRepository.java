package org.jpchase.codingtest.repository;

import org.jpchase.codingtest.domain.Sales;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesRepository {
  private static Logger logger = Logger.getLogger(SalesRepository.class.getName());

  private List<Sales> recordedSales = new ArrayList<>();

  public void add(Sales sales) {
    logger.info("Recording sales : " + sales);
    recordedSales.add(sales);
  }

  public List<Sales> getSales() {
    return recordedSales;
  }
}
